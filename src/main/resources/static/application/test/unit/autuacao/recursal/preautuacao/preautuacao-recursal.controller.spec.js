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
	
	var mockPreferencias = [
		{codigo: 1, descricao: "Maior de 60 anos ou portador de doença grave"},
		{codigo: 2, descricao: "Criminal"},
		{codigo: 3, descricao: "Eleitoral"},
		{codigo: 8, descricao: "Medida Liminar"},
		{codigo: 12, descricao: "Réu Preso"},
		{codigo: 15, descricao: "Assistência Judiciária Gratuita"}
	];
	
	describe('Pré-Autuação de Processo Recursal Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;
		var createController;
		var $httpBackend;
		var properties;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_, ProcessoService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/16').respond(mockPeticao);
			$httpBackend.expectGET(properties.apiUrl + '/classes/tipoprocesso/recursal').respond(classesRecursais);
			
			controller = $controller('PreautuacaoRecursalController', {
				$stateParams: {resources: [16]},
				messages: messages,
				properties: properties,
				ProcessoService: ProcessoService
			});
			
			$httpBackend.flush();
		}));

		it('Deveria inicializar o controlador', function() {
			expect(controller).not.toBeNull();
		});

		it('Deveria carregar corretamente as preferências da classe selecionada', function() {
			controller.classe = "RE";
			$httpBackend.expectGET(properties.apiUrl + '/classes/RE/preferencias').respond(mockPreferencias);
			controller.carregarPreferencias();
			$httpBackend.flush();
			
			expect(controller.preferenciasSelecionadas).toEqual([]);
			expect(controller.preferencias).toEqual(mockPreferencias);
		});
		
		it('Deveria não validar a pré-autuação quando não for selecionada uma classe', function() {
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
		});
	});
})();
