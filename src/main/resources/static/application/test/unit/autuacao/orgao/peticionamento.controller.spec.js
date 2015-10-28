/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.08.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';


	describe('Peticionamento Órgão Controller', function() {

		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $window, $log, properties, FileUploader, OrgaoService) {
			scope = $rootScope.$new();
			$window.sessionStorage.papel = JSON.stringify('representante');
			$httpBackend.expectGET(properties.apiUrl + '/orgaos').respond([{ id : 1, nome : "AGU" }, { id : 2, nome : "PGR" }, { id : 3, nome : "União" }]);			
			
			controller = $controller('PeticionamentoOrgaoController', {
				$scope : scope
			});
			
			$httpBackend.flush();
		}));

		it('Deveria carregar a lista de orgão no escopo do controlador', function() {
			expect(scope.orgaos.length).toEqual(3);
		});
		
	});
})();
