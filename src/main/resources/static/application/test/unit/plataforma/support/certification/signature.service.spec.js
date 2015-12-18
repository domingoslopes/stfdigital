/**
 * Testes unitários do service Signature.
 * 
 * @author Tomas.Godoi
 * 
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Service: Signature', function() {

		var $rootScope;
		
		var fakeCrypto;
		var SignatureService;
		var $httpBackend;
		var properties;
		
		var signingManager;
		
		beforeEach(module('appDev'));
		
		beforeEach(module(function($provide) {
			fakeCrypto = {
				use: function() {},
				getCertificate: function() {},
				sign: function() {}
			};
			
			$provide.value('Crypto', fakeCrypto);
		}));
		
		beforeEach(inject(function(_SignatureService_, _$rootScope_, $q, _$httpBackend_, _properties_) {
			$rootScope = _$rootScope_;
			SignatureService = _SignatureService_;
			signingManager = SignatureService.signingManager();
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			spyOn(fakeCrypto, 'use').and.returnValue(true);
			spyOn(fakeCrypto, 'getCertificate').and.returnValue($q.when({}));
			spyOn(fakeCrypto, 'sign').and.returnValue($q.when({'hex': 'signature-hex'}));
		}));
		
		it('Deveria ter construindo o signingTracker', function() {
			expect(signingManager).not.toBeNull();
		});
		
		it('Deveria ter passado chamado os callbacks', function() {
			$httpBackend.expectPOST(properties.apiUrl + '/certification/signature/prepare').respond({'signerId': 123});
			$httpBackend.expectPOST(properties.apiUrl + '/certification/signature/provide-to-sign').respond({});
			$httpBackend.expectPOST(properties.apiUrl + '/certification/signature/pre-sign').respond({'hash': 'fake-hash', 'hashType': 'SHA-256'});
			$httpBackend.expectPOST(properties.apiUrl + '/certification/signature/post-sign').respond({});
			
			var signer = signingManager.createSigner();
			
			signer.onSignerReady(function(signerId) {
				console.log('controller-onSignerReady');
				console.log(signerId);
				signer.provideExistingDocument("6");
			});
			
			signer.onSigningCompleted(function(signedDocument) {
				console.log('controller-onSigningCompleted');
				console.log(signedDocument);
			});
			
			signer.onErrorCallback(function(error) {
            	console.log('controller-onErrorCallback');
            	console.log(error);
            });
			
			var progressTracker = signer.progressTracker(90);
			expect(progressTracker).not.toBeNull();

			signer.start();
			
			$httpBackend.flush();
			
			$rootScope.$apply();
			
			$rootScope.$apply();
		});
		
	});
})();
