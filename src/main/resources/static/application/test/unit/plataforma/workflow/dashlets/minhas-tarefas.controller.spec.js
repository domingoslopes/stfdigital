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
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $http, $window, properties, TarefaService) {
			$window.sessionStorage.papel = JSON.stringify('recebedor');
			scope = $rootScope.$new();
			$httpBackend.expectGET(properties.apiUrl + '/workflow/tarefas').respond([{descricao : 'Petição #00001'}, {descricao : 'Petição #00002'}]);
			
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
			expect(scope.tarefas[0].descricao).toEqual('Petição #00001');
			expect(scope.tarefas[1].descricao).toEqual('Petição #00002');
			expect(scope.tarefas.length).toEqual(2);
		});
	});
})();
