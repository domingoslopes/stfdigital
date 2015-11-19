/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.autuacao.service('SignatureService', ['properties', '$http', '$q', function(properties, $http, $q) {
		var crypto = hwcrypto;
		
		var requestUserCertificate = function() {
			console.log('requestUserCertificate');
			return $q(function(resolve, reject) {
				crypto.getCertificate({lang: 'en'}).then(function(response) {
					resolve(response);
				}, function(err) {
					reject(err);
				});
			});
		};

		var PrepareCommand = function(certificate) {
			this.certificateAsHex = certificate;
		};
		var prepare = function(certificate) {
			console.log('prepare');
			return $q(function(resolve, reject) {
				var command = new PrepareCommand(certificate.hex);
				$http.post(properties.apiUrl + '/certification/signature/prepare', command).success(function(dto) {
					console.log(dto);
					resolve(dto);
				}).error(function(error) {
					console.log(error);
					reject(error);
				});
			});
		};
		
		var PreSignCommand = function(contextId) {
			this.contextId = contextId;
		};
		var preSign = function(contextId) {
			var command = new PreSignCommand(contextId);
			return $q(function(resolve, reject) {
				$http.post(properties.apiUrl + '/certification/signature/pre-sign', command).success(function(dto) {
					console.log(dto);
					resolve(dto);
				}).error(function(error) {
					console.log(error);
					reject(error);
				});
			});
		};
		
		var sign = function(resolvedObject) {
			console.log(resolvedObject);
			return $q(function(resolve, reject) {
				crypto.sign(resolvedObject.injectedCertificate, {type: 'SHA-256', hex: resolvedObject.hash}, {lang: 'en'}).then(function(response) {
					console.log(response);
					resolve({'signature': response.hex});
				}, function(err) {
					console.log(err);
					reject(err);
				});
			});
		};
		
		var PostSignCommand = function(contextId, signature) {
			this.contextId = contextId;
			this.signatureAsHex = signature;
		};
		var postSign = function(resolvedObject) {
			var command = new PostSignCommand(resolvedObject.injectedContextId, resolvedObject.signature);
			return $q(function(resolve, reject) {
				$http.post(properties.apiUrl + '/certification/signature/post-sign', command).success(function(dto) {
					console.log(dto);
					resolve(dto);
				}).error(function(error) {
					console.log(error);
					reject(error);
				});
			});
		};
		
		function SigningTracker() {
			var self = this;
			
			var contextId;
			var certificate;
			
			var contextCreatedCallback;
			var signingCompletedCallback;
			
			var errorCallback;
			
			var documentUploadDeferred;
			
			this.onContextCreated = function(callback) {
				contextCreatedCallback = callback;
			};
			
			this.onSigningCompleted = function(callback) {
				signingCompletedCallback = callback;
			};
			
			this.onErrorCallback = function(callback) {
				errorCallback = callback;
			};
			
			this.triggerFileUploaded = function() {
				console.log('triggerFileUpload');
				if (documentUploadDeferred) {
					documentUploadDeferred.resolve(contextId);
				}
			};
			
			var callContextCreatedCallback = function(ci) {
				contextId = ci.contextId;
				documentUploadDeferred = $q.defer();
				if (contextCreatedCallback) {
					contextCreatedCallback(contextId);
				} else {
					documentUploadDeferred.reject('Callback ContextCreated não definido.')
				}
				return documentUploadDeferred.promise;
			};
			
			var callSigningCompletedCallback = function(signedDocument) {
				if (signingCompletedCallback) {
					signingCompletedCallback(signedDocument);
				}
			};
			
			var collectCertificate = function(cert) {
				certificate = cert;
				return $q.when(certificate);
			};
			
			var injectCertificate = function(resolvedObject) {
				resolvedObject['injectedCertificate'] = certificate;
				return $q.when(resolvedObject);
			};
			
			var injectContextId = function(resolvedObject) {
				resolvedObject['injectedContextId'] = contextId;
				return $q.when(resolvedObject);
			};
			
			this.start = function() {
				if (crypto.use('auto')) {
					$q.when()
					.then(requestUserCertificate).then(collectCertificate)
					.then(prepare)
					.then(callContextCreatedCallback)
					.then(preSign)
					.then(injectCertificate).then(sign)
					.then(injectContextId).then(postSign)
//					.then(postSign);
					.catch(function(error) {
						console.log('catch');
						console.log(error);
						errorCallback(error);
					});
				} else {
					console.log('Nenhuma implementação encontrada.')
				}
			};
		}
		
		this.signWithExternalUpload = function() {
			return new SigningTracker();
		};
		
	}]);
	
})();
