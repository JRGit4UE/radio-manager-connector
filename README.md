# radio-manager-connector

Connector to integrate visual analysis services from [JOANNEUM RESEARCH](https://www.joanneum.at/en/digital) with [PLUXBOX](http://pluxbox.com) RadioManager, developed as part of the [MARCONI](https://www.projectmarconi.eu/) project.

The connector consists of a subscriber for analysis jobs and a middleware to handle these analysis jobs and forward them to instances of the analysis service.

## Subscriber
The subscriber app is a *Node.js* application which creates a *GraphQL* subscribtion for analysis 
[jobs](https://graphql.org/) and forwards jobs to the job analysis middleware. 

To build the subscriber, open a shell, *cd* into the *create_job* directory and execute:

```
npm install
```
To run the application execute:

```
node index.js
```

## Middleware
The middleware app is a *Tomcat* resteasy servlet that offers a REST API to handle analysis jobs

To build the servlet, open a shell, *cd* into the *fims_ame* directory and execute:

```
mvn clean install -DskipTests=true
```
For installation, copy the war file:
```
mv target/swagger-jaxrs-resteasy-server-1.0.0.war target/fims2-middleware.war 
sudo cp target/fims2-middleware.war $CATALINA_HOME/webapps 
```

To run the server execute:
```
sudo $CATALINA_HOME/bin/startup.sh 
```

## Test
To test the fims workflow locally:

1) Deploy and start your fims-ame-middleware on your local tomcat (port 8080) - should be done via do-build.sh script
2) Start your examples/create-job node.js GraphQL subscriber listener on your local machine on another console
3) Initiate an image file upload with examples/upload-image node.js application on your local machine on another console
4) Get the _id and url of the uploaded image in the imageMedia JSON of your create-job output window
5) Adjust the mediaid in this file by the content of _id
6) Adjust the mediaurl in this file by the content of url
7) Execute this file in a shell, which posts the content to your local middleware

* Adjust these values after you have started an image upload
mediaid="media/75011644"
mediaurl="https://marconi-assets.pluxcustoms.com/download/jrs/vrt/images/2019/07/02/db7f21dddb/original/DjyG0b7UwAAIClv.jpg"

* To test fault handling
http://url-to-server:8081/fims/v2/notifyFault
http://localhost:8080/fims2-middleware/v2/mediaAMENotificationService/notifyFault

* Execute post to the URL at the end of this file, and watch the debug output of your middleware code

```
curl -i \
-X POST \
-H "Content-Type: application/json" \
-d  '{
"mediaAMEJob": "{  \"resourceID\": \"1\",  \"revisionID\": \"null\",  \"resourceCreationDate\": \"Fri Mar 08 14:14:15 UTC 2019\",  \"resourceModifiedDate\": \"Fri Mar 08 14:17:06 UTC 2019\",  \"status\": \"completed\",  \"bmObject\": {   \"bmContents\": [{    \"resourceID\": \"'"${mediaid}"'\",    \"location\": \"'"${mediaurl}"'\"   }]  },  \"priority\": \"medium\",  \"jobStartedTime\": \"Fri Mar 08 14:14:15 UTC 2019\",  \"jobCompletedTime\": \"Fri Mar 08 14:17:06 UTC 2019\",  \"processed\": {   \"percentageProcessedCompleted\": 100  },  \"profile\": {   \"ameTemplate\": {    \"ameAtom\": [],    \"ameTemplateID\": \"fullanalysis\",    \"ameTemplateName\": \"Proxy generation, faces, objects\"   },   \"ameReport\": {    \"bmContentReference\": {     \"resourceID\": \"'"${mediaid}"'\",     \"location\": \"'"${mediaurl}"'\"    },    \"toolInformation\": {     \"name\": \"MARCONI analysis service\"    },    \"ameItemResult\": [{     \"ameItemID\": \"http://id.projectmarconi.eu/item/proxy\",     \"ameItemName\": \"Proxy Generation\",     \"detectionMethod\": \"automatic\",     \"ameItemOutputs\": [{      \"parameterName\": \"ProxyLocation\",      \"value\": \"http://34.255.173.12:8080/data/1/DjyG0b7UwAAIClv.jpg.jpg\"     }]    }, {     \"ameItemID\": \"http://ebu.io/fims/cards/V009\",     \"ameItemName\": \"Face Recognition\",     \"detectionMethod\": \"automatic\",     \"ameItemOutputs\": [{      \"parameterName\": \"ListOfNames\",      \"value\": \"[    {       \\\"m\\\" : \\\"object\\\",       \\\"p\\\" : {          \\\"@confidence\\\" : \\\"1.0\\\",          \\\"@id\\\" : \\\"e157070c-a4c3-46a8-a82a-787e97697ccf\\\",          \\\"@type\\\" : \\\"face\\\",          \\\"Box\\\" : [             0.30781251192092896,             0.15992970764636993,             0.46562498807907104,             0.27504393458366394          ]       },       \\\"s\\\" : \\\"836c145b7cf98767f138e012c87c97af93090edb996c92f9550ce5557cd4bb81\\\",       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073709\\\"    },    {       \\\"m\\\" : \\\"proxy\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"fkf1\\\",          \\\"@objectref\\\" : \\\"e157070c-a4c3-46a8-a82a-787e97697ccf\\\",          \\\"@type\\\" : \\\"keyframe\\\",          \\\"MediaUri\\\" : \\\"data/1/facekeyframes/1/fkf1.png\\\"       },       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073709\\\"    }, {       \\\"m\\\" : \\\"proxy\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"fkf2\\\",          \\\"@objectref\\\" : \\\"e157070c-a4c3-46a8-a82a-787e97697ccf\\\",          \\\"@type\\\" : \\\"keyframe\\\",          \\\"MediaUri\\\" : \\\"data/1/facekeyframes/1/fkf2.png\\\"       },       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073709\\\"    } ] \"     }]    }, {     \"ameItemID\": \"http://ebu.io/fims/cards/V020\",     \"ameItemName\": \"Concept Detection\",     \"detectionMethod\": \"automatic\",     \"ameItemOutputs\": [{      \"parameterName\": \"DetectedConcepts\",      \"value\": \"[    {       \\\"m\\\" : \\\"object\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"person\\\",          \\\"@type\\\" : \\\"object\\\",          \\\"Box\\\" : [ 0, 0.6502636203866432, 0.25, 0.9815465729349736 ]       },       \\\"s\\\" : \\\"836c145b7cf98767f138e012c87c97af93090edb996c92f9550ce5557cd4bb81\\\",       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073710\\\"    },    {       \\\"m\\\" : \\\"object\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"person\\\",          \\\"@type\\\" : \\\"object\\\",          \\\"Box\\\" : [             0.028125000000000001,             0.58435852372583474,             0.21875,             0.76449912126537789          ]       },       \\\"s\\\" : \\\"836c145b7cf98767f138e012c87c97af93090edb996c92f9550ce5557cd4bb81\\\",       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073710\\\"    },    {       \\\"m\\\" : \\\"object\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"person\\\",          \\\"@type\\\" : \\\"object\\\",          \\\"Box\\\" : [             0.22500000000000001,             0.097539543057996489,             0.65312499999999996,             0.7785588752196837          ]       },       \\\"s\\\" : \\\"836c145b7cf98767f138e012c87c97af93090edb996c92f9550ce5557cd4bb81\\\",       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073710\\\"    },    {       \\\"m\\\" : \\\"object\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"laptop\\\",          \\\"@type\\\" : \\\"object\\\",          \\\"Box\\\" : [             0.23281250000000001,             0.77504393673110716,             0.60781249999999998,             0.9411247803163445          ]       },       \\\"s\\\" : \\\"836c145b7cf98767f138e012c87c97af93090edb996c92f9550ce5557cd4bb81\\\",       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073710\\\"    },    {       \\\"m\\\" : \\\"object\\\",       \\\"p\\\" : {          \\\"@id\\\" : \\\"bottle\\\",          \\\"@type\\\" : \\\"object\\\",          \\\"Box\\\" : [             0.7890625,             0.58699472759226712,             0.83125000000000004,             0.65114235500878737          ]       },       \\\"s\\\" : \\\"836c145b7cf98767f138e012c87c97af93090edb996c92f9550ce5557cd4bb81\\\",       \\\"st\\\" : \\\"dynamic\\\",       \\\"t\\\" : \\\"18446744073710\\\"} ] \" }] }]}},  \"notifyAt\": {   \"replyTo\": \"http://url-to-server:8081/fims/v2/notifymediaAMEResult\", \"faultTo\": \"http://url-to-server:8081/fims/v2/notifyFault\"}}"
}' \
http://localhost:8080/fims2-middleware/v2/mediaAMENotificationService/notifymediaAMEResult
```
