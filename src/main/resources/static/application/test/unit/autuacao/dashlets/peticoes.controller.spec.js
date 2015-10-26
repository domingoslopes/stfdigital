/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Dashlet: Controller: MinhasPeticoesDashletController', function() {
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $http, $window, properties) {
			scope = $rootScope.$new();
			$httpBackend.expectGET(properties.apiUrl + '/peticoes').respond([{descricao : 'Petição #00001'}, {descricao : 'Petição #00002'}]);
			
			controller = $controller('MinhasPeticoesDashletController', {
				$scope : scope,
			});
			
			$httpBackend.flush();
		}));

		it('Deveria instanciar o controlador do dashlet de minhas tarefas', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(scope.peticoes[0].descricao).toEqual('Petição #00001');
			expect(scope.peticoes[1].descricao).toEqual('Petição #00002');
			expect(scope.peticoes.length).toEqual(2);
		});
	});
})();
