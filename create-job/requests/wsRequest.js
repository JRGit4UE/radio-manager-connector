const {execute} = require('apollo-link');
const {WebSocketLink} = require('apollo-link-ws');
const {SubscriptionClient} = require('subscriptions-transport-ws');
const {sendGraphqlQuery} = require('./httpRequest');
const websocketLibrary = require('ws');
const gql = require('graphql-tag');


let link;

async function getWsLink() {
    if (!link) {
        link = true;

        const authTokenResponse = await sendGraphqlQuery('{getSubscriptionAuthToken}');
        if (typeof authTokenResponse === 'string') throw new Error(authTokenResponse);
        if (authTokenResponse.errors) throw new Error(authTokenResponse.errors[0].message);
        const authToken = authTokenResponse.data.getSubscriptionAuthToken;

        let websocketUrl = 'ws';
        if (CONFIG.ssl) websocketUrl += 's';
        websocketUrl += '://';
        websocketUrl += CONFIG.engineEndpoint;
        websocketUrl += CONFIG.subscriptionPath
            .replace('{{applicationKey}}', CONFIG.applicationKey)
            .replace('{{authToken}}', authToken)
        ;

        link = new WebSocketLink(
            new SubscriptionClient(
                websocketUrl,
                {reconnect: true},
                websocketLibrary,
            ),
        );
    }

    return link;
}

let link2;

async function getWsLink2() {
    if (!link2) {
        link2 = true;

        const authTokenResponse = await sendGraphqlQuery('{getSubscriptionAuthToken}');
        if (typeof authTokenResponse === 'string') throw new Error(authTokenResponse);
        if (authTokenResponse.errors) throw new Error(authTokenResponse.errors[0].message);
        const authToken = authTokenResponse.data.getSubscriptionAuthToken;

        let websocketUrl = 'ws';
        if (CONFIG.ssl) websocketUrl += 's';
        websocketUrl += '://';
        websocketUrl += CONFIG.engineEndpoint;
        websocketUrl += CONFIG.subscriptionPath
            .replace('{{applicationKey}}', CONFIG.applicationKey)
            .replace('{{authToken}}', authToken)
        ;

        link2 = new WebSocketLink(
            new SubscriptionClient(
                websocketUrl,
                {reconnect: true},
                websocketLibrary,
            ),
        );
    }

    return link2;
}


/**
 *
 * @param subscriptionQuery {gql}
 * @param variables {any}
 * @param successCallback {Function}
 * @param errorCallback {Function}
 */
async function wsRequest(
    subscriptionQuery,
    variables,
    successCallback,
    errorCallback,
) {

    const subscriptionClient = execute(await getWsLink(), {
        query: subscriptionQuery,
        variables: variables,
    });

    return subscriptionClient.subscribe(successCallback, errorCallback);
}

async function wsRequest2(
    subscriptionQuery,
    variables,
    successCallback,
    errorCallback,
) {

    const subscriptionClient = execute(await getWsLink2(), {
        query: subscriptionQuery,
        variables: variables,
    });

    return subscriptionClient.subscribe(successCallback, errorCallback);
}


function closeAll() {
    try{link.subscriptionClient.close(true, true)}
    catch(e){console.log("E1 " + e.toString())}
    try{ link2.subscriptionClient.close(true, true)}
    catch(e){console.log("E2 " + e.toString())}
}

/*
module.exports = wsRequest
wsRequest.__esModule = true;
wsRequest.default = wsRequest;
wsRequest.getWsLink = getWsLink;
*/


module.exports.imageRequest = wsRequest
module.exports.imageRequest.__esModule = true
module.exports.imageRequest.default = wsRequest
module.exports.imageRequest.getWsLink = getWsLink

module.exports.videoRequest = wsRequest2
module.exports.videoRequest.__esModule = true
module.exports.videoRequest.default = wsRequest2
module.exports.videoRequest.getWsLink2 = getWsLink2

module.exports.sendGraphqlQuery = sendGraphqlQuery
module.exports.closeAll = closeAll