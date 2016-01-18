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
		
		beforeEach(inject(function($controller, $httpBackend, $log, properties) {
			$httpBackend.expectGET(properties.apiUrl + '/classes').respond([{sigla : 'AP', nome: 'Ação Penal'}, {sigla : 'ADI', nome: 'Ação Direta de Inconstitucionalidade'}]);
//			$httpBackend.expectGET(properties.apiUrl + '/peticoes/fisicas/2/preautuar').respond({tipoRecebimento : 'Sedex'});
			$httpBackend.whenGET(properties.apiUrl + '/workflow/tarefas').respond([{}]);

			mockMessages = {
				success: function(){}
			};
			
			
			stateParams = {resources: [2]};
			controller = $controller('PreautuacaoController', {
				$stateParams : stateParams,
				$log: $log,
				messages: mockMessages
			});

			spyOn(mockMessages, 'success').and.callThrough();
			
			$httpBackend.flush();
		}));

		it('Deveria carregar identificador da petição no escopo do controlador', function() {
			expect(controller.peticaoId).toEqual(2);
		});
		
		it('Deveria carregar a lista de classes no escopo do controlador', function() {
			expect(controller.classes.length).toEqual(2);
		});
		
		it('Deveria carregar a petição a preautuar no escopo do controlador', function() {
			controller.classe = 'HC';
			controller.peticao.identificacao = '1/2016';
			controller.finalizar();
			expect(mockMessages.success).toHaveBeenCalledWith('Petição <b>1/2016</b> pré-autuada com sucesso.');
			// TODO Complementar esse teste assim que a controller estiver com essa implementação completa.
//			expect(controller.peticao.tipoRecebimento).toEqual('Sedex');
		});
		
	});
})();