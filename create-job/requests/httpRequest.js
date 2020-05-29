const {default: fetch, Headers} = require('node-fetch');
const signHmacRequest = require('./request-signers/signHmacRequest');

/**
 *
 * @param query {string} The graphql query
 * @param variables {Object | null} The graphql variables
 * @param operationName {string | null}
 * @return {Promise<Object>}
 */
function sendGraphqlQuery(query, variables = null, operationName = null) {
    return httpRequest('', {query, variables, operationName});
}

/**
 *
 * @param uri {string} Application request path. Will always be prefix with the application endpoint
 * @param body {Object|string|number|boolean} The json stringify body. When not using a json body you must enter a string
 * @param method {'GET'|'POST'|'PUT'|'PATCH'|'HEAD'|'DELETE'} The request method
 * @param headers {Headers} The headers you want to send with the request. Must be an instance of Headers
 * @param options {Object} Extra options (Can be handy by some auth methods)
 * @return {Promise<Object>}
 */
function httpRequest(uri,
                     body,
                     method = 'POST',
                     headers = new Headers(),
                     options = {},
) {

    let requestUrl = 'http';
    if (CONFIG.ssl) requestUrl += 's';
    requestUrl += '://';
    requestUrl += CONFIG.engineEndpoint;
    requestUrl += CONFIG.applicationPath
        .replace('{{applicationKey}}', CONFIG.applicationKey)
    ;
    requestUrl += uri;

    let modifiedRequestParams;
    if (/^sha/.test(CONFIG.apiAuthMethod)) {
        modifiedRequestParams = signHmacRequest(requestUrl, body, method, headers, options) || {};
    } else if (CONFIG.apiAuthMethod) {
        // modifiedRequestParams = signJwtRequest() || {};
    }


    requestUrl = modifiedRequestParams.requestUrl || requestUrl;
    body = modifiedRequestParams.body || body;
    method = modifiedRequestParams.method || method;
    headers = modifiedRequestParams.headers || headers;

    return fetch(requestUrl, {headers, body, method})
        .then(res => (res.status === 401) ? res.text() : res.json());
}

module.exports = httpRequest;
httpRequest.sendGraphqlQuery = sendGraphqlQuery;
httpRequest.httpRequest = httpRequest;
httpRequest.default = httpRequest;
httpRequest.Headers = Headers;
httpRequest.__esModule = true;
