/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0M6
 * @since 12.02.2016
 */ 
/* jshint undef:false*/
(function() {
	'use strict';
	
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
		
		beforeEach(inject(function(_$httpBackend_, $controller, $state, messages, _properties_, ProcessoService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/motivos').respond(mockMotivos);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/17/processo').respond(mockProcesso);
			
			controller = $controller('AnalisePressupostoController', {
				$stateParams: {resources: [17], task: { id : '1', metadado : { informacao : '1', tipoInformacao: 'PeticaoFisica' }}},
				messages: messages,
				properties: properties,
				$state: $state,
				ProcessoService: ProcessoService
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
			
			it('Deveria não validar a análise quando não informar o motivo da inaptidão', function() {
				controller.obsAnalise = 'Descrição genérica';
				controller.obsMotivo = 'Motivo genérico';
				controller.motivoId = '';
				expect(controller.validar()).toBeFalsy();
			});

			it('Deveria não validar a análise quando não informar uma descrição para a análise', function() {
				controller.obsAnalise = '';
				controller.obsMotivo = 'Motivo genérico';
				controller.motivoId = 9;
				expect(controller.validar()).toBeFalsy();
			});
			
			it('Deveria não validar a análise quando não informar o motivo da inaptidão', function() {
				controller.obsAnalise = 'Descrição genérica';
				controller.obsMotivo = '';
				controller.motivoId = 9;
				expect(controller.validar()).toBeFalsy();
			});
			
			it('Deveria validar a análise quando informar uma descrição para a análise, o motivo da inaptidão e a observação do motivo', function() {
				controller.obsAnalise = 'Descrição genérica';
				controller.obsMotivo = 'Motivo genérico';
				controller.motivoId = 9;
				expect(controller.validar()).toBeTruthy();
			});
		});
	});
})();
