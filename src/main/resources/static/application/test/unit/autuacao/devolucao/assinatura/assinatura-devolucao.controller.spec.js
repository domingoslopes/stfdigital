/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 07.12.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Assinatura Devolução Controller', function() {

		var scope;
		
		var fakePeca;
		
		var fakePecaService;
		var fakePeticaoService;
		var fakeMessages;
		var fakeSigner;
		
		var initController = function(resources) {
			return function($rootScope, $controller, $q) {
				scope = $rootScope.$new();
				
				var stateParams = {'resources': resources};
				
				fakePeticaoService = {
					consultar: function(){},
					assinarDevolucao: function(){}
				};
				
				fakePecaService = {
					montarUrlConteudo: function(){}	
				};

				fakeMessages = {
					error: function(){}
				};
				
				fakeSigner = new function() {
					var self = this;
					
					var callbackSignerReady;
					var callbackSigningCompleted;
					var callbackErrorCallback;
					
					this.onSignerReady = function(callback) {
						callbackSignerReady = callback;
					};
					
					this.onSigningCompleted = function(callback) {
						callbackSigningCompleted = callback;
					};
					
					this.onErrorCallback = function(callback) {
						callbackErrorCallback = callback;
					};
					
					var deferred = $q.defer();
					this.triggerDocumentProvided = function() {
						callbackSigningCompleted("url-download");
					};
					
					this.provideExistingDocument = function() {
						self.triggerDocumentProvided();
					};
					
					this.saveSignedDocument = function() {
						return $q.when();
					};
					
					this.start = function(){
						callbackSignerReady("123");						
					}
				};
				
				spyOn(fakeSigner, 'start').and.callThrough();
				spyOn(fakeSigner, 'onSignerReady').and.callThrough();
				spyOn(fakeSigner, 'onSigningCompleted').and.callThrough();
				spyOn(fakeSigner, 'onErrorCallback').and.callThrough();
				spyOn(fakeSigner, 'triggerDocumentProvided').and.callThrough();
				spyOn(fakeSigner, 'provideExistingDocument').and.callThrough();
				spyOn(fakeSigner, 'saveSignedDocument').and.callThrough();
				
				var fakeSigningManager = {
					createSigner: function(){
						return fakeSigner;
					}
				};
				
				var fakeSignatureService = {
					signingManager: function() {
						return fakeSigningManager;
					}
				};
				
				fakePeca = {
					"tipoId" : 8,
					"tipoNome" : "Ofício",
					"descricao" : "Ofício nº 1000",
					"documentoId" : 2
				};
				
				var fakePeticao = {
					"id" : 6,
					"numero" : 6,
					"ano" : 2015, 
					"identificacao" : "6/2015",
					"classe" : "AP",
					"partes" : {
						"PoloAtivo" : [],
						"PoloPassivo" : []
					},
					"pecas" : [fakePeca],
					"processoWorkflowId" : 6,
					"volumes" : 10,
					"apensos" : 10,
					"formaRecebimento" : "SEDEX",
					"numeroSedex" : "10"
				};
				
				spyOn(fakePeticaoService, 'consultar').and.returnValue($q.when(fakePeticao));
				spyOn(fakePeticaoService, 'assinarDevolucao').and.returnValue($q.when());
				
				spyOn(fakePecaService, 'montarUrlConteudo').and.returnValue('url-fake');
				
				spyOn(fakeMessages, 'error').and.callThrough();
				
				var controller = $controller('AssinaturaDevolucaoController', {
					$scope : scope,
					$stateParams: stateParams,
					PeticaoService: fakePeticaoService,
					PecaService: fakePecaService,
					messages: fakeMessages,
					SignatureService: fakeSignatureService
				});
			};
		};
		
		beforeEach(module('appDev'));
		
		describe('Controller com objetos de resources', function() {
			
			beforeEach(inject(initController([{'peticaoId': 6}])));
			
			it('Deveria detectar resources como ids a partir de objetos', function() {
				scope.$apply();
				expect(fakePeticaoService.consultar).toHaveBeenCalledWith(6);
			});
		});
		
		describe('Controller com ids de resources', function() {
			
			beforeEach(inject(initController([6])));
			
			it('Deveria carregar a lista de petições no escopo do controlador', function() {
				scope.$apply();
				expect(scope.peticoes.length).toEqual(1);
			});
			
			it('Deveria detectar a peça do documento de devolução', function() {
				scope.$apply();
				var peca = scope.pecaDocumentoDevolucao(scope.peticoes[0]);
				expect(peca.documentoId).toEqual(2);
			});
			
			it('Deveria delegar a montagem da url de conteúdo da peça para PecaService', function() {
				scope.$apply();
				var url = scope.urlConteudo(fakePeca);
				expect(url).toEqual('url-fake');
				expect(fakePecaService.montarUrlConteudo).toHaveBeenCalledWith(fakePeca);
			});
			
			it('Deveria validar que documentos não foram assinados', function() {
				scope.$apply();
				scope.finalizar();
				expect(fakeMessages.error).toHaveBeenCalledWith("É necessário assinar os documentos antes de finalizar.");
			});
			
			it('Deveria assinar os documentos', function() {
				scope.$apply();
				
				scope.assinar();
				
				scope.$apply();
				
				expect(fakeSigner.onSignerReady).toHaveBeenCalled();
				expect(fakeSigner.onSigningCompleted).toHaveBeenCalled();
				expect(fakeSigner.onErrorCallback).toHaveBeenCalled();
				expect(fakeSigner.provideExistingDocument).toHaveBeenCalledWith(2);
				expect(fakeSigner.saveSignedDocument).toHaveBeenCalled();
				
				scope.finalizar();
				expect(fakeMessages.error).not.toHaveBeenCalled();
				expect(fakePeticaoService.assinarDevolucao).toHaveBeenCalledWith([{'peticaoId': 6, 'documentoId': 2}]);
			});
		});
		
		
	});
})();
