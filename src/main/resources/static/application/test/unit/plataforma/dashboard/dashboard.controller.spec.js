/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Dashboard', function() {
		var fakeData = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $http, $window, properties, TarefaService) {
			scope = $rootScope.$new();
			$window.sessionStorage.papel = JSON.stringify('recebedor');
			$httpBackend.expectGET(properties.apiUrl + '/tarefas').respond([{descricao : 'Petição #00001'}, {descricao : 'Petição #00002'}]);

			TarefaService.listar().success(function(result) {
				fakeData = result;
			});
			
			$httpBackend.flush();
			
			controller = $controller('DashboardController', {
				$scope : scope,
				data : {
					data : fakeData
				}
			});
		}));

		it('Não deveria ser "null"', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria ter "dados" dentro de seu escopo ($scope)', function() {
			scope.$apply();
			expect(scope.tarefas[0].descricao).toEqual('Petição #00001');
			expect(scope.tarefas[1].descricao).toEqual('Petição #00002');
			expect(scope.tarefas.length).toEqual(2);
		});
	});
})();
