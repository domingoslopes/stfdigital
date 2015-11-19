/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.08.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Peticionamento Advogado Controller', function() {

		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $window, $log, properties, FileUploader, ClasseService) {
			scope = $rootScope.$new();
			$window.sessionStorage.papel = JSON.stringify('peticionador');
			$httpBackend.expectGET(properties.apiUrl + '/classes').respond([{sigla : 'AP', nome: 'Ação Penal'}, {sigla : 'ADI', nome: 'Ação Direta de Inconstitucionalidade'}]);
			
			ClasseService.listar().success(function(result) {
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
			expect(scope.classes.length).toEqual(2);
		});
		
	});
})();
