/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Distribuição Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $http, properties, MinistroService) {
			scope = $rootScope.$new();
			$httpBackend.expectGET(properties.apiUrl + '/ministros').respond([{id : 42, nome: 'Min. Cármen Lúcia'}, {id : 44, nome: 'Min. Dias Toffoli'}]);

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

		it('Deveria carregar o identificador da petição no escopo do controlador', function() {
			expect(scope.idPeticao).toEqual(1);
		});
		
		it('Deveria carregar a lista de ministros no escopo do controlador', function() {
			expect(scope.ministros[0].nome).toEqual('Min. Cármen Lúcia');
			expect(scope.ministros[1].nome).toEqual('Min. Dias Toffoli');
			expect(scope.ministros.length).toEqual(2);
		});
		
	});
})();
