/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('SignatureService', function(properties, $http, $q, Crypto) {
		var requestUserCertificate = function(alreadySelectedCertificate) {
			console.log('requestUserCertificate');
			return $q(function(resolve, reject) {
				if (!alreadySelectedCertificate) {
					Crypto.getCertificate({lang: 'en'}).then(function(response) {
						resolve(response);
					}, function(err) {
						reject(err);
					});
				} else {
					resolve(alreadySelectedCertificate);
				}
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
		
		var PreSignCommand = function(signerId) {
			this.signerId = signerId;
		};
		var preSign = function(signerId) {
			var command = new PreSignCommand(signerId);
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
				Crypto.sign(resolvedObject.injectedCertificate, {type: 'SHA-256', hex: resolvedObject.hash}, {lang: 'en'}).then(function(response) {
					console.log(response);
					resolve({'signature': response.hex});
				}, function(err) {
					console.log(err);
					reject(err);
				});
			});
		};
		
		var PostSignCommand = function(signerId, signature) {
			this.signerId = signerId;
			this.signatureAsHex = signature;
		};
		var postSign = function(resolvedObject) {
			var command = new PostSignCommand(resolvedObject.injectedSignerId, resolvedObject.signature);
			return $q(function(resolve, reject) {
				$http.post(properties.apiUrl + '/certification/signature/post-sign', command).success(function(dto) {
					console.log(dto);
					resolve({'downloadUrl': properties.apiUrl + '/certification/signature/download-signed/' + resolvedObject.injectedSignerId});
				}).error(function(error) {
					console.log(error);
					reject(error);
				});
			});
		};
		
		var ProvideToSignCommand = function(signerId, documentId) {
			this.signerId = signerId;
			this.documentId = documentId;
		};
		
		var Signer = function(collectCertificate, injectCertificate, injectAlreadySelectedCertificate) {
			var self = this;
			
			var signerId;
			
			var signerCreatedCallback;
			var signingCompletedCallback;

			var errorCallback;
			
			var documentUploadDeferred;
			
			var currentStep = 0;
			var totalSteps = 12.0;
			
			this.progressTracker = function(totalProgress) {
				return {
					currentProgress: function() {
						return (currentStep / totalSteps) * totalProgress;
					}
				};
			};
			
			var trackProgress = function(func) {
				return function() {
					console.log(currentStep);
					currentStep++;
					return func.apply(this, arguments);
				};
			};
			
			// Callbacks
			this.onSignerReady = function(callback) {
				signerCreatedCallback = callback;
			};
			
			this.onSigningCompleted = function(callback) {
				signingCompletedCallback = callback;
			};
			
			this.onErrorCallback = function(callback) {
				errorCallback = callback;
			};
			
			// Trigger
			this.triggerDocumentProvided = function() {
				console.log('triggerFileUpload');
				if (documentUploadDeferred) {
					documentUploadDeferred.resolve(signerId);
				}
			};
			
			this.provideExistingDocument = function(documentId) {
				var command = new ProvideToSignCommand(signerId, documentId);
				$http.post(properties.apiUrl + '/certification/signature/provide-to-sign', command).then(function(response) {
					console.log(response.data);
					self.triggerDocumentProvided();
				}, function(response) {
					console.log(response.data);
					errorCallback(response.data);
				});
			};
			
			this.saveSignedDocument = function() {
				return $http.post(properties.apiUrl + '/certification/signature/save-signed/' + signerId, {}).then(function(response) {
					console.log(response.data);
					return response.data;
				}, function(response) {
					console.log(response.data);
					return response.data;
				});
			};
			
			var callSignerReadyCallback = function(ci) {
				signerId = ci.signerId;
				documentUploadDeferred = $q.defer();
				if (signerCreatedCallback) {
					signerCreatedCallback(signerId);
				} else {
					documentUploadDeferred.reject('Callback SignerCreated não definido.')
				}
				return documentUploadDeferred.promise;
			};
			
			var callSigningCompletedCallback = function(signedDocument) {
				if (signingCompletedCallback) {
					signingCompletedCallback(signedDocument);
				}
			};
			
			var injectSignerId = function(resolvedObject) {
				resolvedObject['injectedSignerId'] = signerId;
				return $q.when(resolvedObject);
			};
			
			this.start = function() {
				currentStep = 0;
				if (Crypto.use('auto')) {
					currentStep++;
					$q.when().then(trackProgress(injectAlreadySelectedCertificate)) // Injeta o certificado se já tiver sido selecionado.
					.then(trackProgress(requestUserCertificate)).then(trackProgress(collectCertificate))
					.then(trackProgress(prepare))
					.then(trackProgress(callSignerReadyCallback))
					.then(trackProgress(preSign))
					.then(trackProgress(injectCertificate)).then(trackProgress(sign))
					.then(trackProgress(injectSignerId)).then(trackProgress(postSign))
					.then(trackProgress(callSigningCompletedCallback))
					.catch(function(error) {
						console.log('catch');
						console.log(error);
						errorCallback(error);
					});
				} else {
					console.log('Nenhuma implementação encontrada.')
				}
			};
		};
		
		var SigningManager = function() {
			var certificate;
			
			var collectCertificate = function(cert) {
				if (!certificate) {
					certificate = cert;
					deferredRequestingFirstCertificate.resolve();
				}
				return $q.when(certificate)
			};
			
			var injectCertificate = function(resolvedObject) {
				console.log('injectCertificate');
				console.log(resolvedObject);
				resolvedObject['injectedCertificate'] = certificate;
				return $q.when(resolvedObject);
			};
			
			var deferredRequestingFirstCertificate;
			
			var injectAlreadySelectedCertificate = function() {
				return $q(function(resolve) {
					if (certificate) {
						console.log('existing-certificate');
						resolve(certificate);
					} else if (!deferredRequestingFirstCertificate) {
						console.log('creating-deferred');
						deferredRequestingFirstCertificate = $q.defer();
						resolve();
					} else {
						console.log('else');
						deferredRequestingFirstCertificate.promise.then(function() {
							console.log('resolvedFirstCertificate');
							resolve(certificate);
						});
					}
				});
			};
			
			this.createSigner = function() {
				return new Signer(collectCertificate, injectCertificate, injectAlreadySelectedCertificate);
			}
		};
		
		this.signerWithExternalUpload = function() {
			return new SigningTracker();
		};
		
		this.signingManager = function() {
			return new SigningManager();
		};
		
	});
	
})();
