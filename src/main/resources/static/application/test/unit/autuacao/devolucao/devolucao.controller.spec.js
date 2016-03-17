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
	
	var classesRecursais = [
	    {sigla: "RE", nome: "Recurso Extraordinário"},
		{sigla: "ARE", nome: "Recurso Extraordinário com Agravo"},
		{sigla: "AI", nome: "Agravo de Instrumento"}
	];
	
	var mockMotivoDevolucao = [
	    {id : 'REMESSA_INDEVIDA', nome : "Remessa Indevida"}, 
	    {id : 'TRANSITADO', nome : "Transitado"}, 
	    {id : 'BAIXADO', nome : "Baixado"}
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
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_, ClasseService, PeticaoService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/16').respond(mockPeticao);
			$httpBackend.expectGET(properties.apiUrl + '/tipodevolucao').respond(mockMotivoDevolucao);
			$httpBackend.expectGET(properties.apiUrl + '/classes/tipoprocesso/recursal').respond(classesRecursais);
			
			scope = $rootScope.$new();
			
			controller = $controller('DevolucaoController', {
				$scope: scope,
				$stateParams: {resources: [16]},
				messages: messages,
				properties: properties,
				$state: $state,
				ClasseService: ClasseService,
				PeticaoService: PeticaoService
			});
			
			$httpBackend.flush();
		}));

		it('Deveria inicializar o controlador', function() {
			expect(controller).not.toBeNull();
		});

		it('Deveria carregar corretamente os modelos pelo motivo de devolução selecionado', function() {
			controller.tipoDevolucao = "TRANSITADO";
			$httpBackend.expectGET(properties.apiUrl + '/tipoDevolucao/TRANSITADO/modelos').respond(mockModelos);
			controller.carregarModelos();
			$httpBackend.flush();
			
			expect(controller.preferenciasSelecionadas).toEqual([]);
			expect(controller.preferencias).toEqual(mockPreferencias);
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
