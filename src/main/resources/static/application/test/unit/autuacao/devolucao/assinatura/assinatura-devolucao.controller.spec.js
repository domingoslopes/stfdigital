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
		
		var initController = function(resources) {
			return function($rootScope, $controller, $q) {
				scope = $rootScope.$new();
				
				var stateParams = {'resources': resources};
				
				fakePeticaoService = {
					consultar: function(){}
				};
				
				fakePecaService = {
					montarUrlConteudo: function(){}	
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
				
				spyOn(fakePecaService, 'montarUrlConteudo').and.returnValue('url-fake');
				
				var controller = $controller('AssinaturaDevolucaoController', {
					$scope : scope,
					$stateParams: stateParams,
					PeticaoService: fakePeticaoService,
					PecaService: fakePecaService
				});
			};
		};
		
		beforeEach(module('appDev'));
		
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
				var url = scope.urlConteudo(fakePeca);
				expect(url).toEqual('url-fake');
				expect(fakePecaService.montarUrlConteudo).toHaveBeenCalledWith(fakePeca);
			});
		});
		
		describe('Controller com objetos de resources', function() {
			
			beforeEach(inject(initController([{'peticaoId': 6}])));
			
			it('Deveria detectar resources como ids a partir de objetos', function() {
				scope.$apply();
				expect(fakePeticaoService.consultar).toHaveBeenCalledWith(6);
			});
		});
		
	});
})();
