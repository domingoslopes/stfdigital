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
			return $q(function(resolve, reject) {
				if (!alreadySelectedCertificate) {
					Crypto.getCertificate({lang: 'en'}).then(function(response) {
						resolve(response);
					}, function(err) {
						resolve({'error': err}); // O tratamento de erro será feito no collect certificate, de modo a rejeitar as outras promises.
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
			return $q(function(resolve, reject) {
				var command = new PrepareCommand(certificate.hex);
				$http.post(properties.apiUrl + '/certification/signature/prepare', command).success(function(dto) {
					resolve(dto);
				}).error(function(error) {
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
					resolve(dto);
				}).error(function(error) {
					reject(error);
				});
			});
		};
		
		var sign = function(resolvedObject) {
			return $q(function(resolve, reject) {
				Crypto.sign(resolvedObject.injectedCertificate, {data: resolvedObject.data, type: 'SHA-256', hex: resolvedObject.hash}, {lang: 'en'}).then(function(response) {
					resolve({'signature': response.hex});
				}, function(err) {
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
					resolve({'downloadUrl': properties.apiUrl + '/certification/signature/download-signed/' + resolvedObject.injectedSignerId});
				}).error(function(error) {
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
				if (documentUploadDeferred) {
					documentUploadDeferred.resolve(signerId);
				}
			};
			
			this.provideExistingDocument = function(documentId) {
				var command = new ProvideToSignCommand(signerId, documentId);
				$http.post(properties.apiUrl + '/certification/signature/provide-to-sign', command).then(function(response) {
					self.triggerDocumentProvided();
				}, function(response) {
					errorCallback(response.data);
				});
			};
			
			this.saveSignedDocument = function() {
				return $http.post(properties.apiUrl + '/certification/signature/save-signed/' + signerId, {}).then(function(response) {
					return response.data;
				}, function(response) {
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
				Crypto.use('auto').then(function(status) {
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
						if (error.message === 'no_implementation') {
							errorCallback("O plugin de assinatura não foi encontrado.");
						} else {
							errorCallback(error);
						}
					});
				}, function(err) {
					errorCallback("O plugin de assinatura não foi encontrado.");
				});
			};
		};
		
		var SigningManager = function() {
			var certificate;
			
			var collectCertificate = function(cert) {
				return $q(function(resolve, reject) {
					if (cert.error) {
						deferredRequestingFirstCertificate.reject(cert.error);
						reject(cert.error);
					} else {
						if (!certificate) {
							certificate = cert;
							deferredRequestingFirstCertificate.resolve();
						}
						resolve(certificate);
					}
				})
			};
			
			var injectCertificate = function(resolvedObject) {
				resolvedObject['injectedCertificate'] = certificate;
				return $q.when(resolvedObject);
			};
			
			var deferredRequestingFirstCertificate;
			
			var injectAlreadySelectedCertificate = function() {
				return $q(function(resolve, reject) {
					if (certificate) {
						resolve(certificate);
					} else if (!deferredRequestingFirstCertificate) {
						deferredRequestingFirstCertificate = $q.defer();
						resolve();
					} else {
						deferredRequestingFirstCertificate.promise.then(function() {
							resolve(certificate);
						}, function(error) {
							reject(error);
						});
					}
				});
			};
			
			this.createSigner = function() {
				return new Signer(collectCertificate, injectCertificate, injectAlreadySelectedCertificate);
			}
		};
		
		this.signingManager = function() {
			return new SigningManager();
		};
		
	});
	
})();
