/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0M6
 * @since 12.02.2016
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	var mockProcessoRecursal = {
		assuntos: [],
		classe: "RE",
		id: 8,
		identificacao: "RE 5",
		numero: 5,
		observacaoAnalise: "123",
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [],
		preferencias: [],
		relator: null,
		situacao: "A analisar",
		teses: []
	};
	
	var mockPeticao = {
		ano: 2016,
		apensos: 1,
		classe: "RE",
		formaRecebimento: "SEDEX",
		id: 15,
		identificacao: "7/2016",
		numero: 7,
		numeroSedex: "1",
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [],
		preferencias: null,
		processoWorkflowId: 10001,
		tipo: "PeticaoFisica",
		tipoProcesso: "RECURSAL",
		volumes: 1
	};
	
	var mockTese = [{
		assuntos: [
	        {codigo: "4292", descricao: "Jurisdição e Competência"},
            {codigo: "10913", descricao: "Medidas Assecuratórias"}
        ],
        codigo: 171,
        descricao: "Recurso extraordinário em que se discute, à luz dos artigos 5º, XXXVII e LIII; 93, III; 94 e 98, I, da Constituição Federal, a nulidade, ou não, de julgamento realizado por órgão fracionário de tribunal, composto majoritariamente por juízes convocados, tendo em conta os princípios do juiz natural e do duplo grau de jurisdição.",
        numero: 171,
        tipoTese: "REPERCUSSAO_GERAL"
	}];
	
	describe('Revisão de Repercussão Geral Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;
		var createController;
		var $httpBackend;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $log, messages, properties, $state, ProcessoService, PeticaoService) {
			$httpBackend = _$httpBackend_;
			
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/14').respond(mockPeticao);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/14/processo').respond(mockProcessoRecursal);
			
			scope = $rootScope.$new();
			controller = $controller('AnaliseRepercussaoGeralController', {
				$scope: scope,
				$stateParams: {resources: [14]},
				$log: $log,
				messages: messages,
				properties: properties,
				$state: $state,
				ProcessoService: ProcessoService,
				PeticaoService: PeticaoService
			});
			
			$httpBackend.flush();
		}));

		it('Deveria inicializar o controlador', function() {
			expect(controller).not.toBeNull();
		});
		
		it('Deveria carregar informações do processo recursal', function() {
			expect(controller.processo).toEqual(mockProcessoRecursal);
		});
		
		it('Deveria carregar informações da petição', function() {
			expect(controller.peticao).toEqual(mockPeticao);
		});
		
		it('Deveria validar o formulário quando os parâmetros não forem alterados', function() {
			expect(controller.validar()).toBeTruthy;
		});
		
		it('Deveria adicionar uma tese e seus assuntos na lista', function() {
			var qtdAssuntos = controller.assuntos.length;
			var qtdTeses = controller.teses.length;
			
			controller.tipoTese = {id:"REPERCUSSAO_GERAL", descricao : "Repercussão Geral"};
			controller.numeroTese = 171;
			
			$httpBackend.expectGET('/api/teses?numero=171&tipo=REPERCUSSAO_GERAL').respond(mockTese);
			controller.consultarTese();
			$httpBackend.flush();
			
			expect(controller.assuntos.length).toBe(qtdAssuntos + 2);
			expect(controller.teses.length).toBe(qtdTeses + 1);
		});
		
		it('Deveria adicionar uma tese na lista quando não houver tese de mesmo código na lista', function() {
			var qtdTeses = controller.teses.length;
			
			controller.adicionarTese({
				"codigo": 172
			});
			
			expect(controller.teses.length).toBe(qtdTeses + 1);
		});
		
		it('Deveria não adicionar uma tese na lista quando houver tese de mesmo código na lista', function() {
			var qtdTeses = controller.teses.length;
			
			controller.adicionarTese({
				"codigo": 173
			});
			controller.adicionarTese({
				"codigo": 173
			});
			
			expect(controller.teses.length).toBe(qtdTeses + 1);
		});
		
		it('Deveria remover tese e seus assuntos quando houver o código na lista', function() {
			var qtdAssuntos = controller.assuntos.length;
			var qtdTeses = controller.teses.length;
			
			controller.adicionarAssuntoNaLista({
				"codigo": "9999",
				"descricao": "Assunto genérico 1"
			});
			
			controller.adicionarTese({
				"codigo": 173,
				
				"assuntos": [
					{
						"codigo": "9999",
						"descricao": "Assunto genérico 1"
					}
             	]
			});
			
			controller.removerTese({
				"codigo": 173,
				
				"assuntos": [
					{
						"codigo": "9999",
						"descricao": "Assunto genérico 1"
					}
             	]
			});
			
			expect(controller.assuntos.length).toBe(qtdAssuntos);
			expect(controller.teses.length).toBe(qtdTeses);
		});
		
		it('Deveria adicionar um assunto na lista quando não houver mesmo código na lista', function() {
			var qtdAssuntos = controller.assuntos.length;
			
			controller.adicionarAssuntoNaLista({
				"codigo": "9999",
				"descricao": "Assunto genérico 1"
			});
			
			expect(controller.assuntos.length).toBe(qtdAssuntos + 1);
		});
		
		it('Deveria não adicionar um assunto na lista quando houver mesmo código na lista', function() {
			var qtdAssuntos = controller.assuntos.length;

			controller.adicionarAssuntoNaLista({
				"codigo": "9998",
				"descricao": "Assunto genérico 2"
			});
			controller.adicionarAssuntoNaLista({
				"codigo": "9998",
				"descricao": "Assunto genérico 2"
			});
			controller.adicionarAssuntoNaLista({
				"codigo": "9997",
				"descricao": "Assunto genérico 2"
			});
			
			expect(controller.assuntos.length).toBe(qtdAssuntos + 2);
		});
		
		it('Deveria não validar o formulário quando não houver assuntos', function() {
			controller.assuntos = [];
			expect(controller.validar()).toBeTruthy;
		});
		
		it('Deveria não validar o formulário quando não houver teses', function() {
			controller.teses = [];
			expect(controller.validar()).toBeTruthy;
		});
		
		it('Deveria não validar o formulário quando não houver análise', function() {
			controller.observacao = '';
			expect(controller.validar()).toBeTruthy;
		});
	});
})();
