/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.08.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Peticionamento Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $window, $log, properties, OrgaoService) {
			scope = $rootScope.$new();
			$window.sessionStorage.papel = JSON.stringify('representante');
			$httpBackend.expectGET(properties.apiUrl + '/orgaos').respond([{ id : 1, nome : "AGU" }, { id : 2, nome : "PGR" }, { id : 3, nome : "União" }]);
			
			OrgaoService.listar().success(function(result) {
				fakeData = result;
			});
			
			$httpBackend.flush();
			
			controller = $controller('PeticionamentoController', {
				$scope : scope,
				data : {
					data : fakeData
				}
			});
		}));

		it('Deveria carregar a lista de classes no escopo do controlador', function() {
			expect(scope.orgaos.length).toEqual(3);
		});
		
	});
})();
