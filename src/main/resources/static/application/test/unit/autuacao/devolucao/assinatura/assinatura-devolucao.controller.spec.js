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
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $q) {
			scope = $rootScope.$new();
			
			var stateParams = {resources: [6]};
			
			var fakePeticaoService = {
				consultar: function(){}
			};
			

			var fakePeticao = {
				"id" : 6,
				"numero" : 6,
				"classe" : "AP",
				"partes" : {
					"PoloAtivo" : [],
					"PoloPassivo" : []
				},
				"pecas" : [{
					"tipoId" : 8,
					"tipoNome" : "Ofício",
					"descricao" : "Ofício nº 1000",
					"documentoId" : 2
				}],
				"processoWorkflowId" : 6,
				"volumes" : 10,
				"apensos" : 10,
				"formaRecebimento" : "SEDEX",
				"numeroSedex" : "10"
			};
			
			spyOn(fakePeticaoService, 'consultar').and.returnValue($q.when(fakePeticao));
			
			var controller = $controller('AssinaturaDevolucaoController', {
				$scope : scope,
				$stateParams: stateParams,
				PeticaoService: fakePeticaoService
			});
		}));
		
		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(scope.peticoes.length).toEqual(1);
		});
		
	});
})();
