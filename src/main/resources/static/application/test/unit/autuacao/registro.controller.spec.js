/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.00
 * @since 01.09.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Registro Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, $log, properties) {
// TODO Melhorar esse teste. A chamada abaixo não está sendo feita ainda.			
//			$httpBackend.expectGET(properties.apiUrl + '/tiporecebimentos').respond([{sigla : '2', nome: 'Sedex'}, {sigla : '3', nome: 'Malote'}]);
			
			stateParams = {idTarefa: 4};
			
			
			controller = $controller('RegistroPeticaoFisicaController', {
				$stateParams : stateParams,
				$log: $log
			});
// TODO Melhorar esse teste. Chamada abaixo foi comentada, pois estava causando erro. Nada do $http está sendo chamado ainda.
//			$httpBackend.flush();
		}));
		
		it ('Deveria carregar a lista de forma de envio do controlador', function(){
			expect(controller.tipoRecebimentos.length).toEqual(5); // TODO Atualizar este teste quando fizer a chamada realmente ao back-end /tiporecebimentos
		});
	});
})();