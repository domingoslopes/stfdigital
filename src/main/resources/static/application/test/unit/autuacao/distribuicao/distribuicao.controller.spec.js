/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Distribuicao Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('app'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $http, $window, properties, MinistroService) {
			scope = $rootScope.$new();
			$window.sessionStorage.papel = JSON.stringify('distribuidor');
			$httpBackend.expectGET(properties.apiUrl + '/ministros').respond([{id : 42, nome: 'MIN. CÁRMEN LÚCIA'}, {id : 44, nome: 'MIN. DIAS TOFFOLI'}]);

			MinistroService.listar().success(function(result) {
				fakeData = result;
			});
			
			$httpBackend.flush();
			stateParams = {idTarefa: 1};
			
			controller = $controller('DistribuicaoController', {
				$scope : scope,
				data : {
					data : fakeData
				},
				$stateParams : stateParams
			});
		}));

		it('Deveria carregar a lista de Ministros no escopo do controlador', function() {
			expect(scope.ministros[0].nome).toEqual('MIN. CÁRMEN LÚCIA');
			expect(scope.ministros[1].nome).toEqual('MIN. DIAS TOFFOLI');
			expect(scope.ministros.length).toEqual(2);
		});
		
		it('Deveria carregar ID da Petição no escopo do controlador', function() {
			expect(scope.idPeticao).toEqual(1);
		});
		
	});
})();
