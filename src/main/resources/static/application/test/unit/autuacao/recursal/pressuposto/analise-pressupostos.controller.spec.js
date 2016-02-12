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
		classe: "RE",
		formaRecebimento: "SEDEX",
		id: 17,
		identificacao: "9/2016",
		numero: 9,
		numeroSedex: "3",
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [],
		preferencias: null,
		processoWorkflowId: 10044,
		tipo: "PeticaoFisica",
		tipoProcesso: "RECURSAL",
		volumes: 1
	};
	
	var mockProcesso = {
		assuntos: [],
		classe: "RE",
		id: 9,
		identificacao: "RE 6",
		numero: 6,
		observacaoAnalise: null,
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [],
		preferencias: [],
		relator: null,
		situacao: "A analisar",
		teses: []
	}
	
	var mockMotivos = [
		{id: 2, descricao: "Ausência de preliminar formal de repercussão geral"},
		{id: 3, descricao: "Decisão agravada com base na sistemática da repercussão geral (art. 543-B, CPC)"},
		{id: 4, descricao: "Intempestividade do agravo"},
		{id: 5, descricao: "Intempestividade do recurso extraordinário"},
		{id: 9, descricao: "Outro"},
		{id: 6, descricao: "Recurso extraordinário contra decisão monocrática"},
		{id: 7, descricao: "Recurso extraordinário julgado deserto na origem"},
		{id: 8, descricao: "Supressão de instância"}
	];
	
	describe('Análise de Pressupostos Formais de Processo Recursal Controller', function() {
		var controller;
		var $httpBackend;
		var properties;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_, ClasseService, PeticaoService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/motivos').respond(mockMotivos);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/17').respond(mockPeticao);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/17/processo').respond(mockProcesso);
			
			var scope = $rootScope.$new();
			
			controller = $controller('AnalisePressupostoController', {
				$scope: scope,
				$stateParams: {resources: [17]},
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

		describe('Quando classificar o processo como apto', function() {
			beforeEach(function() {
				controller.apto = true;
			});
			
			it('Deveria não validar a análise quando não informar uma descrição para a análise', function() {
				controller.obsAnalise = '';
				expect(controller.validar()).toBeFalsy();
			});
			
			it('Deveria validar a análise quando informar uma descrição para a análise', function() {
				controller.obsAnalise = 'Descrição genérica';
				expect(controller.validar()).toBeTruthy();
			});
		});

		describe('Quando classificar o processo como inapto', function() {
			beforeEach(function() {
				controller.apto = false;
			});
			
			it('Deveria não validar a análise quando não informar uma descrição para a análise', function() {
				controller.obsAnalise = '';
				expect(controller.validar()).toBeFalsy();
			});

			it('Deveria não validar a análise quando informar o motivo da inaptidão mas não informar uma descrição para a análise', function() {
				controller.obsMotivo = 'Motivo genérico';
				expect(controller.validar()).toBeFalsy();
			});
			
			it('Deveria não validar a análise quando informar uma descrição para a análise mas não informar o motivo da inaptidão', function() {
				controller.obsAnalise = 'Descrição genérica';
				expect(controller.validar()).toBeFalsy();
			});
			
			it('Deveria validar a análise quando informar uma descrição para a análise e o motivo da inaptidão', function() {
				controller.obsAnalise = 'Descrição genérica';
				controller.obsMotivo = 'Motivo genérico';
				expect(controller.validar()).toBeTruthy();
			});
		});
	});
})();
