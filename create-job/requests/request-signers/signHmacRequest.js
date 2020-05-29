const url = require('url');
const crypto = require('crypto');
const {Headers} = require('node-fetch');
const uuidV4 = require('uuid/v4');

/**
 *
 * @param requestUrl {string} Application request path. Will always be prefix with the application endpoint
 * @param body {Object|string|number|boolean} The json stringify body. When not using a json body you must enter a string
 * @param method {'GET'|'POST'|'PUT'|'PATCH'|'HEAD'|'DELETE'} The request method
 * @param headers {Headers} The headers you want to send with the request. Must be an instance of Headers
 * @param reqOptions {object} Extra options for the request
 * @return {Promise<Object>}
 */
function signHmacRequest(requestUrl, body, method, headers, reqOptions) {

    const applicationApiSecretBuffer = Buffer.from(CONFIG.apiSecret, 'base64'); // decoded apiSecret

    const realm = 'Acquia';
    const version = '2.0';

    const urlComponents = url.parse(requestUrl);
    const timestamp = Math.floor(new Date().getTime() / 1000).toString();
    const nonce = uuidV4();

    const authorizationHeaderParameters = [
        'id=' + encodeURIComponent(CONFIG.apiKey),
        'nonce=' + encodeURIComponent(nonce),
        'realm=' + encodeURIComponent(realm),
        'version=' + encodeURIComponent(version),
    ].join('&');

    const hashComponents = [
        method.toUpperCase(),
        urlComponents.host,
        urlComponents.pathname,
        urlComponents.query,
        authorizationHeaderParameters,
    ];

    const headersToSign = (reqOptions.hashHeaders || [])
        .map(header => header.toLowerCase())
        .filter(header => headers.has(header))
        .sort();

    const signHeaders = headersToSign
        .map(key => `${key}:${headers.get(key)}`)
        .join('\n') || null;

    if (signHeaders) {
        hashComponents.push(signHeaders);
    }

    // Add timestamp
    hashComponents.push(timestamp);

    let hashedContent = '';

    if (!['GET', 'HEAD'].includes(method)) {
        let contentType = 'application/json';
        if (headers.has('Content-Type')) {
            contentType = headers.get('Content-Type');
        } else {
            headers.append('Content-Type', contentType);
        }

        if (contentType === 'application/json') {
            // Stringify body
            body = JSON.stringify(body);
        }

        hashedContent = crypto.createHash(CONFIG.apiAuthMethod)
            .update(body).digest('base64');

        hashComponents.push(contentType);
        hashComponents.push(hashedContent);
    }

    const HMACSignature = crypto.createHmac(CONFIG.apiAuthMethod, applicationApiSecretBuffer)
        .update(hashComponents.join('\n')).digest('base64');


    const HMACAuthorization = 'acquia-http-hmac ' + [
        `id="${encodeURIComponent(CONFIG.apiKey)}"`,
        `nonce="${encodeURIComponent(nonce)}"`,
        `realm="${encodeURIComponent(realm)}"`,
        `version="${encodeURIComponent(version)}"`,
        `headers="${headersToSign.join(';')}"`,
        `signature="${encodeURIComponent(HMACSignature)}"`,
    ].join(',');

    if(JSON.stringify(body).indexOf('getSubscriptionAuthToken1') > 0)
    {
        console.log('HMACAuthorization=')
        console.log(HMACAuthorization)
    }

    headers.append('Authorization', HMACAuthorization);
    headers.append('X-Authorization-Timestamp', timestamp);
    headers.append(`X-Authorization-Content-${CONFIG.apiAuthMethod}`, hashedContent);

    return {
        requestUrl,
        body,
        method,
        headers,
    };
}

module.exports = signHmacRequest;
signHmacRequest.default = signHmacRequest;
signHmacRequest.__esModule = true;
