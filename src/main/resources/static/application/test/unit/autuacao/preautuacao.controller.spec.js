/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.09.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Preautuação Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var mockMessages;
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($controller, $httpBackend, $window, $log, properties) {
			$window.sessionStorage.papel = JSON.stringify('preautuador');
			$httpBackend.expectGET(properties.apiUrl + '/classes').respond([{sigla : 'AP', nome: 'Ação Penal'}, {sigla : 'ADI', nome: 'Ação Direta de Inconstitucionalidade'}]);
//			$httpBackend.expectGET(properties.apiUrl + '/peticoes/fisicas/2/preautuar').respond({tipoRecebimento : 'Sedex'});
			$httpBackend.expectGET(properties.apiUrl + '/workflow/tarefas').respond([{}]);

			mockMessages = {};
			stateParams = {idTarefa: 2};
			controller = $controller('PreautuacaoController', {
				$stateParams : stateParams,
				$log: $log,
				messages: mockMessages
			});

			$httpBackend.flush();
			
		}));

		it('Deveria carregar identificador da petição no escopo do controlador', function() {
			expect(controller.idPeticao).toEqual(2);
		});
		
		it('Deveria carregar a lista de classes no escopo do controlador', function() {
			expect(controller.classes.length).toEqual(2);
		});
		
		it('Deveria carregar a petição a preautuar no escopo do controlador', function() {
			controller.classe = 'HC';
			controller.finalizar();
			// TODO Complementar esse teste assim que a controller estiver com essa implementação completa.
//			expect(controller.peticao.tipoRecebimento).toEqual('Sedex');
		});
		
	});
})();