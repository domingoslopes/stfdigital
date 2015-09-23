/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Dashboard Controller', function() {
		var fakeData = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $http, $window, properties, TarefaService) {
			scope = $rootScope.$new();
			$window.sessionStorage.papel = JSON.stringify('recebedor');
			$httpBackend.expectGET(properties.apiUrl + '/workflow/tarefas').respond([{descricao : 'Petição #00001'}, {descricao : 'Petição #00002'}]);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes').respond([{descricao : 'Petição #00001'}, {descricao : 'Petição #00002'}]);

			TarefaService.listar().success(function(result) {
				fakeData = result;
			});
			
			controller = $controller('DashboardController', {
				$scope : scope,
				data : {
					data : fakeData
				}
			});
			
			$httpBackend.flush();
		}));

		it('Deveria instanciar o controlador do dashboard', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria carregar a lista de tarefas no escopo do controlador', function() {
			scope.$apply();
			expect(scope.tarefas[0].descricao).toEqual('Petição #00001');
			expect(scope.tarefas[1].descricao).toEqual('Petição #00002');
			expect(scope.tarefas.length).toEqual(2);
		});

		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(scope.peticoes[0].descricao).toEqual('Petição #00001');
			expect(scope.peticoes[1].descricao).toEqual('Petição #00002');
			expect(scope.peticoes.length).toEqual(2);
		});
	});
})();
