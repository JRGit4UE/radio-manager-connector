
// FTT this is our fake aws-sdk implementation for S3 on a local file
// (preferably mounted from outside the docker container)

const fs = require('fs')
const crypto = require('crypto')

const key = Buffer.from("12345678901234567890123456789012")

function encrypt(text) {
    let iv = crypto.randomBytes(16)
    let cipher = crypto.createCipheriv('aes-256-cbc', key, iv);
    let encrypted = cipher.update(text);
    encrypted = Buffer.concat([encrypted, cipher.final()]);
    return { iv: iv.toString('hex'), encryptedData: encrypted.toString('hex') };
}

function decrypt(blob) {
    let iv = Buffer.from(blob.iv, 'hex');
    let encryptedText = Buffer.from(blob.encryptedData, 'hex');
    let decipher = crypto.createDecipheriv('aes-256-cbc', key, iv);
    let decrypted = decipher.update(encryptedText);
    decrypted = Buffer.concat([decrypted, decipher.final()]);
    return decrypted.toString();
}

// Namespace aws
var aws = {
    S3: function(dummy){
        return {
            getObject: function(p){
                console.log(`Trying to read bucket ${p.Bucket}`)
                return{
                    promise: function(){
                        return new Promise((resolve, reject) => {
                            try{
                                let buf = fs.readFileSync(p.Bucket)
                                let json = JSON.parse(buf)
                                let enc = json[p.Key];
                                resolve({Body: decrypt(enc)})
                            }
                            catch(e){
                                reject("nada " + p.Bucket + " " + e )
                            }
                        })
                    }
                }
            },
            putObject: function(p, cb){
                try{
                    let data = `{"${p.Key}": ${JSON.stringify(encrypt(p.Body), null, 2)}}`
                    fs.writeFileSync(p.Bucket, data)
                }
                catch(e){
                    return cb(e) // trigger err
                }
                return cb(null, "ok") // trigger ok
            }
        }
    }
}

module.exports = aws;
