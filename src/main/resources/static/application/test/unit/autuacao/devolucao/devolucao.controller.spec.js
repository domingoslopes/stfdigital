/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0M6
 * @since 12.02.2016
 */ 
/* jshint undef:false*/
(function() {
	'use strict';
	
	var mockPeticao = {
		ano: 2016,
		apensos: 1,
		classe: null,
		formaRecebimento: "SEDEX",
		id: 16,
		identificacao: "8/2016",
		numero: 8,
		numeroSedex: "2",
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [],
		preferencias: null,
		processoWorkflowId: 10028,
		tipo: "PeticaoFisica",
		tipoProcesso: "RECURSAL",
		volumes: 1
	};
	
	var mockMotivoDevolucao = [
	    {id : 1, descricao : "Remessa Indevida"}, 
	    {id : 2, descricao : "Transitado"}, 
	    {id : 3, descricao : "Baixado"}
	];
	
	var mockModelos = [
   	    {id : 1, nome : "Ofício"}, 
   	    {id : 2, nome : "Certidão"}, 
   	    {id : 3, nome : "Intimação"}
   	];
	
	describe('Devolução de Processo Recursal Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;
		var createController;
		var $httpBackend;
		var properties;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_, PeticaoService, ModeloService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/16/processo-workflow').respond(mockPeticao);
			$httpBackend.expectGET(properties.apiUrl + '/motivos-devolucao').respond(mockMotivoDevolucao);
			
			scope = $rootScope.$new();
			
			controller = $controller('DevolucaoController', {
				$scope: scope,
				$stateParams: {resources: [16]},
				messages: messages,
				properties: properties,
				$state: $state,
				PeticaoService: PeticaoService,
				ModeloService : ModeloService
			});
			
			$httpBackend.flush();
		}));

		it('Deveria inicializar o controlador', function() {
			expect(controller).not.toBeNull();
		});
		
		it('Deveria carregar corretamente os modelos pelo motivo de devolução selecionado', function() {
			controller.motivoDevolucao = 1;
			$httpBackend.expectGET(properties.apiUrl + '/motivos-devolucao/1/modelos').respond(mockModelos);
			
			expect(controller.modelos.length).toEqual(3);
		});
		
	/*	it('Deveria não validar a pré-autuação quando não for selecionada uma classe', function() {
			controller.classe = "";
			expect(controller.validar()).toBeFalsy();
		});
		
		it('Deveria validar a pré-autuação quando for selecionada uma classe', function() {
			controller.classe = "ARE";
			expect(controller.validar()).toBeTruthy();
		});
		
		it('Deveria não validar a devolução quando não for informado um motivo', function() {
			controller.motivo = "";
			expect(controller.validarDevolucao()).toBeFalsy();
		});
		
		it('Deveria validar a devolução quando for informado um motivo', function() {
			controller.motivo = "Motivo genérico";
			expect(controller.validarDevolucao()).toBeTruthy();
		});*/
	});
})();
