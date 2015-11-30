/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Dashlet Minhas Tarefas: Controller', function() {
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, properties) {
			scope = $rootScope.$new();
			$httpBackend.expectGET(properties.apiUrl + '/workflow/tarefas').respond([
				{"id":1,"nome":"autuacao","descricao":"Autuar Processo","processoWorkflow":6,"metadado":{"informacao":6,"tipoInformacao":"PeticaoEletronica","status":"A_AUTUAR","descricao":"6/2015"},"tipoInformacao":"PeticaoEletronica"},
				{"id":2,"nome":"autuacao","descricao":"Autuar Processo","processoWorkflow":7,"metadado":{"informacao":7,"tipoInformacao":"PeticaoEletronica","status":"A_AUTUAR","descricao":"7/2015"},"tipoInformacao":"PeticaoEletronica"}
			]);
			
			controller = $controller('MinhasTarefasDashletController', {
				$scope : scope,
			});
			
			$httpBackend.flush();
		}));

		it('Deveria instanciar o controlador do dashlet de minhas tarefas', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(scope.tarefas[0].metadado.descricao).toEqual('6/2015');
			expect(scope.tarefas[1].metadado.descricao).toEqual('7/2015');
			expect(scope.tarefas.length).toEqual(2);
		});
	});
})();
