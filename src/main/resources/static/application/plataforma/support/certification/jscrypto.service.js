/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 17.12.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('JsCryptoFactory', function($q) {
		
		var base64ToArrayBuffer = function(base64) {
		    var binaryString =  window.atob(base64);
		    var len = binaryString.length;
		    var bytes = new Uint8Array(len);
		    for (var i = 0; i < len; i++)        {
		        bytes[i] = binaryString.charCodeAt(i);
		    }
		    return bytes.buffer;
		};
		
		var hex2ArrayBuffer = function(str) {
            var len = Math.floor(str.length / 2);
            var ret = new Uint8Array(len);
            for (var i = 0; i < len; i++) {
                ret[i] = parseInt(str.substr(i * 2, 2), 16);
            }
            return ret.buffer;
	    };
	    
	    var arrayBuffer2hex = function(args) {
	        var ret = "";
	        var arr = new Uint8Array(args);
	        for (var i = 0; i < arr.length; i++) {
	        	ret += (arr[i] < 16 ? "0" : "") + arr[i].toString(16);
	        }
	        return ret.toLowerCase();
	    };
		
		var Signature = function(sc) {
			var signatureContent = sc;
			
			this.asHex = function() {
				return arrayBuffer2hex(signatureContent);
			};
			
			this.asUint8Array = function() {
				return new Uint8Array(10);
			};
		};

		var JsCrypto = function(pk, cert) {
			var privateKey = pk;
			var certificate = cert;
			
			this.certificateAsUint8Array = function() {
				return new Uint8Array(10);
			};
			
			this.certificateAsHex = function() {
				return certificate;
			};
			
			this.sign = function(hash) {
				return $q(function(resolve, reject) {
					window.crypto.subtle.sign({
							name: 'RSASSA-PKCS1-v1_5'
						},
						privateKey,
						hex2ArrayBuffer(hash)
					).then(function(sig) {
						resolve(new Signature(sig));
					}).catch(function(err) {
						reject(err);
					});
				});
			};
		};
		
		this.create = function(privateKey, certificate) {
			return $q(function(resolve, reject) {
				window.crypto.subtle.importKey('pkcs8', base64ToArrayBuffer(privateKey), {
					name: 'RSASSA-PKCS1-v1_5',
					hash: {name: 'SHA-256'}
				}, false, ['sign']).then(function(pk) {
					resolve(new JsCrypto(pk, certificate));
				}).catch(function(err) {
					reject(err);
				});
			});
		};
		
	});
	
})();
