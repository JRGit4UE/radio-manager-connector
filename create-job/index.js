//////////////////////////////////////////////////////////////////////////////////////////////////
// Create subscription and send job to worker if image/video upload is (subscription) triggered //
// MIT License (C) 2019-2020 JOANNEUM RESEARCH                                                  //
//////////////////////////////////////////////////////////////////////////////////////////////////
'use strict';

const version="1.1";

const gql = require('graphql-tag');
const wsRequest = require("./requests/wsRequest"); // From Pluxbox
const {sendGraphqlQuery} = require('./requests/httpRequest');
const {videoRequest} = require('./requests/wsRequest');
const {imageRequest} = require('./requests/wsRequest');
const fetch_retry = require('fetch-retry');
const todo = require('memory-cache');

const cfgTemplate = require('./cfgTemplate.json')
const fs = require('fs');

const aws = require("./my-aws-sdk"); // Simple substitute


function getConfigFromPath(path){
    let file = path + "MediaAMESubscriptionService.json"
    console.log(`Trying to read config ${file}`)
    let data = ""
    try{
        data = fs.readFileSync(file, 'utf8');
    }catch(e){
        console.log(`Error loading ${file}: ${e}`)
        return {};
    }
    try {
        let cfg = JSON.parse(data);
        if(cfg.version !== cfgTemplate.version) {
            console.log(`Error config version mismatch: ${cfg.version} vs. expected ${cfgTemplate.version}`);
        }
        return cfg;
    }
    catch(e) {
        console.log(`Error parsing ${file}\nExpected format:\n${JSON.stringify(cfgTemplate, null, 2)}`);
    }
    return {};
}

let configPathWithTrailingSlash = process.env.JOB_HANDLER_CFG_PATH || "/fims_config_subscription/";
global.CONFIG = getConfigFromPath(configPathWithTrailingSlash);

const s3 = new aws.S3({
    apiVersion: '2006-03-01',
});

var targeturl = CONFIG.middlewareUrl || "url-not-set";


const subscriptionQuery1 = gql`subscription ($permission: ProfilePermission!, $filters: Integration_pbMessenger_pbMessenger_MessageFilters) {
    pbMessenger_pbMessenger_createMessage(profilePermission: $permission, filters: $filters) {
        _key
        videoMediaObjects {
            _key
            locations {
                original
            }
        }
    }
}`;


const subscriptionQuery2 = gql`subscription ($permission: ProfilePermission!, $filters: Integration_pbMessenger_pbMessenger_MessageFilters) {
    pbMessenger_pbMessenger_createMessage(profilePermission: $permission, filters: $filters) {
        _key
        imageMediaObjects {
            _key
            locations {
                original
            }
        }
    }
}`;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

process.on('SIGINT', function() {
    console.log("Caught interrupt signal");
    process.exitCode = 3;
})


function getQueryString (field, url) {
    let reg = new RegExp( '[?&]' + field + '=([^&#]*)', 'i' );
    let string = reg.exec(url);
    return string ? string[1] : null;
}


const mySleep = (milliseconds) => {
    return new Promise(resolve => setTimeout(resolve, milliseconds));
}


function getMediaUrl(item) {
    if (item.locations !== null){
        return item.locations.original;
    }
    return null;
}

function getImageMediaObjects(item) {
    if(item !== null && item.node !== null && item.node.message !== null && item.node.message.imageMediaObjects !== null){
        return item.node.message.imageMediaObjects;
    }
    console.log(`No imageMediaObjects in ${JSON.stringify(item, null, 2)}`);
    return [];
}

function getVideoMediaObjects(item) {
    if(item !== null && item.node !== null && item.node.message !== null && item.node.message.videoMediaObjects !== null){
        return item.node.message.videoMediaObjects;
    }
    console.log(`No videoMediaObjects in ${JSON.stringify(item, null, 2)}`);
    return [];
}

function isUnique(key, container) {
    //console.log(`isUnique=${(container.get(key) == null)}`)
    let result = (container.get(key) === null);
    return result;
}

async function queueJobs() {
    console.log("Queue jobs..")

    // We simplify the queueing algorithm and only care about media assets uploaded within a time window of N days.
    let days = 1;
    let timewindow = {
        LastRecievedAt: (function (d) {
            d.setDate(d.getDate() - days);
            return d;
        })(new Date())
    };
    //console.log(`starting from = ${timewindow.LastRecievedAt}`)

    // Get last processed media asset id from s3, if available
    let lastMediaId = 'unknown';
    try {
        let params = {Bucket: global.CONFIG.s3BucketName, Key: global.CONFIG.s3BucketKey};
        let data = await s3.getObject(params).promise();
        let json = JSON.parse(data.Body);
        console.log(`Successfully got data from myBucket/myKey: lastMediaId=${json.mediaId}, timestampUTC=${json.timestampUTC}`);
        lastMediaId = json.mediaId;
//        timewindow.LastRecievedAt = new Date(parseInt(json.timestampUTC)-1000);
//        console.log(`s3 timeframe: ${JSON.stringify(lastUpload, null, 2)}`);
    } catch (e) {
        console.log(`Error getting object from s3 bucket: ${e}`);
    }

    const get_uploads = `
    query ($LastRecievedAt: DateTime){
        pbChannels {
            defaultChannel {
                messages {
                    use_media_for_distribution(
                        paginate: { first: 100 }
                    filters: { receivedAt: { gt: $LastRecievedAt } }
                    ) {
                        edges {
                            node {
                                message {
                                   _key
                                   stationId
                                   imageMediaObjects { # for images, is [] for videos
                                       _key
                                       locations {
                                           original
                                       }
                                   }
                                   videoMediaObjects { # for videos, is [] for images
                                       _key
                                       locations {
                                           original
                                       }
                                   }
                                }
                            }
                        }
                    }
                }
            }
        }
    }`;

    let result={}
    try {
        result = await sendGraphqlQuery(get_uploads, timewindow);

        console.log(`get_uploads = ${JSON.stringify(result, null, 2)}`)
        //console.log(Array.isArray(result.data.channels.defaultChannel.messages.use_media_for_distribution.edges))
        let items = result.data.pbChannels.defaultChannel.messages.use_media_for_distribution.edges;
        if (items.length === 0){
            console.log("No uploads found");
        }

        items.forEach((item) => { // Iterate over edges
            // images
            let mediaObjects = getImageMediaObjects(item)
            //console.log(`mediaObjects = ${JSON.stringify(mediaObjects, null,2)}`)
            mediaObjects.forEach((media) => {
                let mediaId = media._key;
                //console.log(`mediaId=${mediaId} - lastMediaId=${lastMediaId}`);
                if (mediaId === lastMediaId) {
                    console.log(`Found last processed mediaId ${mediaId}, clear todo list.`)
                    todo.clear(); // Throw away all media assets so far, as they should be already processed
                } else {
                    // Check for duplicates
                    if(isUnique(mediaId, todo)) {
                        //console.log(`get mediaUrl ${JSON.stringify(item, null, 2)}`)
                        let mediaUrl = getMediaUrl(media)
                        if (mediaUrl !== null) {
                            console.log(`Queing unprocessed ${mediaId} (${mediaUrl})`);
                            todo.put(mediaId, mediaUrl);
                        }
                    } else {
                        console.log(`Skip duplicate ${mediaId}`)
                    }
                }
            })
            // videos
            mediaObjects = getVideoMediaObjects(item)
            mediaObjects.forEach((media) => {
                let mediaId = media._key;
                //console.log(`mediaId=${mediaId} - lastMediaId=${lastMediaId}`);
                if (mediaId === lastMediaId) {
                    console.log(`Found last processed mediaId ${mediaId}, clear todo list.`)
                    todo.clear(); // Throw away all media assets so far, as they should be already processed
                } else {
                    // Check for duplicates
                    if(isUnique(mediaId, todo)) {
                        let mediaUrl = getMediaUrl(media)
                        if (mediaUrl !== null) {
                            console.log(`Queing unprocessed ${mediaId} (${mediaUrl})`);
                            todo.put(mediaId, mediaUrl);
                        }
                    } else {
                        console.log(`Skip duplicate ${mediaId}`)
                    }
                }
            });
        });

        // Subscribe to image and video upload events

        ///// Subscribe for videos /////////////////////////////////////////////////////////////////////////////////////////
        videoRequest(
            subscriptionQuery1,
            {
                permission: 'read_message_in_studio',
                filters: {
                    messageTo: 'CHANNEL',
                    videoMediaIds: {
                        eqn: []
                    }
                }
            },
            eventData => { // Do something on receipt of the event
                console.log(`video_eventData = ${JSON.stringify(eventData, null, 2)}`);
                let i = 0
                for (i = 0; i < eventData.data.pbMessenger_default_createMessage.imageMediaObjects.length; i++) {
                    let id = eventData.data.pbMessenger_default_createMessage.videoMediaObjects[i]._key;
                    let url = eventData.data.pbMessenger_default_createMessage.videoMediaObjects[i].locations.original;
                    console.log(`Received video event: ${id} (${url})`)
                    todo.put(id, url)

                    sendGraphqlQuery(`mutation {
                    analysis_contentAnalysisResult {
                        create(
                            data: {
                                status: queued # State is now queued!
                                extractionTimeStamp: 0
                                mediaId: "${id}"
                            }
                            ) {
                                _id
                            }
                        }
                    }`).then(status => console.log(`status:  ${status}`));
                }
            },
            err => {
                console.log(`Video websocket error: ${err}`);
            }
        )
            .then(t => {
                console.log("Video websocket listener is up and running..");
            })
            .catch((err) => {
                console.log(`Video websocket listener error ${err}`);
                process.exitCode = 1;
            });


        //// subscribe for images //////////////////////////////////////////////////////////////////////////////////////////
        imageRequest(
            subscriptionQuery2,
            {
                permission: 'read_message_in_studio',
                filters: {
                    messageTo: 'CHANNEL',
                    imageMediaIds: {
                        eqn: []
                    }
                }
            },
            eventData => { // Do something on receipt of the event
                //console.log(`image_eventData = ${JSON.stringify(eventData, null, 2)}`);
                let i = 0
                for (i = 0; i < eventData.data.pbMessenger_default_createMessage.imageMediaObjects.length; i++) {
                    let id = eventData.data.pbMessenger_default_createMessage.imageMediaObjects[i]._key;
                    let url = eventData.data.pbMessenger_default_createMessage.imageMediaObjects[i].locations.original;
                    console.log(`Received image event: ${id} (${url})`);
                    todo.put(id, url)
                    sendGraphqlQuery(`mutation {
                    analysis_contentAnalysisResult {
                        create(
                            data: {
                                status: queued # State is now queued!
                                extractionTimeStamp: 0
                                mediaId: "${id}"
                            }
                        ) {
                            _id
                        }
                    }
                    }`).then(status => console.log(`json status:  ${JSON.stringify(status, null, 2)}`));
                }
            },
            err => {
                console.log(`Image websocket error: ${err}`);
            }
        )
            .then((t) => {
                console.log("Image websocket listener is up and running..");
            })
            .catch((err) => {
                console.log(`Image websocket listener error ${err}`);
                process.exitCode = 2;
            });
    } catch (e) {
        console.log(`E3: ${e}`);
    }
}

function checkRetry(attempts, err, response) {

    if (attempts >= 3) {
        // TODO either network issue or this job has an issue
        console.log('Maximum number of tries has been reached - bail out')
        try { // Since we've added the media id as query param, we can retrieve it in case of an error
            let id = getQueryString('mediaId', response.url)
            console.log(`id = ${id}`);
            sendGraphqlQuery(
                `mutation {
                   analysis_contentAnalysisResult {
                     create(
                       data: {
                         status: failed # Set state to failed!
                         extractionTimeStamp: 0
                         mediaId: "${id}"
                       }
                     ) {
                       _id
                     }
                   }
                }`
            ).then(status => console.log(`status:  ${status}`));
        } catch (e) {
            console.log(`E4: ${e}`);
        }
        return false;
    }


    // Trigger retry on HTTP server errors
    if ([500, 501, 502, 503].indexOf(response.status) >= 0) {
        // console.log(`status: ${response.status}, retry necessary`)
        if (process.exitCode > 0) {
            console.log('Stop retries on exit request')
            return false;
        }
        return true;
    }

    return false;
}

// Main func
const handleJobs = async () => {

    // FTT to test if the server exists -  calling a simple GraphQL query
    //await testConnection(); console.log("Connection-test done - bail out"); return;

    let delayMs = 5000;

    process.exitCode = 0;

    // Handle unprocessed jobs and subscribe to new uploaded media assets
    queueJobs();

    // Main job handler loop
    while(process.exitCode === 0) {

        await mySleep(delayMs);

        console.log("Checking todo list...")

        let mediaIds = todo.keys()

        if (mediaIds.length === 0) {
            continue;
        }

        let id = mediaIds[0]
        console.log(`Handle id ${id}`)

        let mediaurl = todo.get(id)
        todo.del(id)

        console.log(`Post job creation data to ${targeturl}`)

        // We add the mediaId in case of an error, which triggers a status failed message
        fetch_retry(targeturl + `?mediaId=${id}`, {
            method: 'post',
            headers: {'Content-Type': 'application/json'},
            body: `
                {
                    "status": "new",
                    "bmObject": {
                        "bmContents": [
                            {
                                "resourceID": "${id}",
                                "location": "${mediaurl}"
                            }
                        ]
                    },
                    "priority": "medium",
                    "profile": {
                        "ameTemplate": {
                            "ameTemplateID": "fullanalysis"
                        }
                    }
                }`
            ,
            retryDelay: 30000,
            retryOn: checkRetry
        })
            .then(res => {
                if (res.ok) {
                    // Set status to processing
                    sendGraphqlQuery(
                        `mutation {
                   analysis_contentAnalysisResult {
                    create(
                       data: {
                         status: processing # State is now processing!
                         extractionTimeStamp: 0
                         mediaId: "${id}"
                       }
                    ){
                      _id
                     }
                    }
                }`
                    ).then(status => {
                        //console.log(`status:  ${status}`;
                        // Set data in storage

                        // FTT Note, that mediaUrl is not really used for now,
                        // as the real mediaUrl comes from Pluxbox GraphQL query

                        let params = {
                            Bucket: global.CONFIG.s3BucketName,
                            Key: global.CONFIG.s3BucketKey,
                            Body: `{"mediaId": "${id}", "mediaUrl": "${mediaurl}", "timestampUTC": "${Date.now()}"}`
                        };

                        s3.putObject(params, function (err, data) {
                            if (err) {
                                console.log(`Error putting object in s3 bucket: ${err}`)
                            } else {
                                console.log("Successfully uploaded data to myBucket/myKey");
                            }
                        });

                    });
                } else {
                    console.log(`Ooops not yet there: ${res.status} <-------`);
                }
            })
            .catch(err => {
                console.log(`received error: ${err}`);
                process.exitCode = 4;
            })
    }

    // Wait for gentle exit
    wsRequest.closeAll()
    console.log("Gentle exit")
}

// Main entry point
handleJobs()
