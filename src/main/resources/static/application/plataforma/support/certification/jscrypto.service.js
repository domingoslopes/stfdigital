/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 17.12.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('JsCryptoFactory', function($q, Converter) {
		
		var Signature = function(sc) {
			var signatureContent = sc;
			
			this.asHex = function() {
				return Converter.arrayBuffer2hex(signatureContent);
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
			
			this.sign = function(data) {
				return $q(function(resolve, reject) {
					window.crypto.subtle.sign({
							name: 'RSASSA-PKCS1-v1_5'
						},
						privateKey,
						Converter.hex2ArrayBuffer(data.data)
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
				window.crypto.subtle.importKey('pkcs8', Converter.base64ToArrayBuffer(privateKey), {
					name: 'RSASSA-PKCS1-v1_5',
					hash: {name: 'SHA-256'}
				}, true, ['sign']).then(function(pk) {
					resolve(new JsCrypto(pk, certificate));
				}).catch(function(err) {
					reject(err);
				});
			});
		};
		
	});
	
})();
