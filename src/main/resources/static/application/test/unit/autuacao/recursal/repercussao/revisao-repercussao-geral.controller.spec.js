/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0M6
 * @since 03.02.2016
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	var mockProcessoRecursal = {
		"id": 7,
		"numero": 4,
		"classe": "RE",
		"relator": null,
		"partes": {
			"Polo Ativo": [],
			"Polo Passivo": []
		},
		"pecas": [],
		"situacao": "A analisar",
		"preferencias": [],
		"identificacao": "RE 4",
		"teses": [
			{
				"codigo": 170,
				"descricao": "Recurso extraordinário em que se discute, à luz dos artigos 5º, XXXVII e LIII; 93, III; 94 e 98, I, da Constituição Federal, a nulidade, ou não, de julgamento realizado por órgão fracionário de tribunal, composto majoritariamente por juízes convocados, tendo em conta os princípios do juiz natural e do duplo grau de jurisdição.",
				"numero": 170,
				"assuntos": [
					{
						"codigo": "4291",
						"descricao": "Jurisdição e Competência"
					},
					{
						"codigo": "10912",
						"descricao": "Medidas Assecuratórias"
					}
				],
				"tipoTese": "REPERCUSSAO_GERAL"
			}
		],
		"assuntos": [
			{
				"codigo": "4291",
				"descricao": "Jurisdição e Competência"
			},
			{
				"codigo": "10912",
				"descricao": "Medidas Assecuratórias"
			}
		],
		"observacaoAnalise": "123"
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
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, messages, properties, $state, ProcessoService) {
			$httpBackend = _$httpBackend_;
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/14/processo').respond(mockProcessoRecursal);
			
			scope = $rootScope.$new();
			controller = $controller('RevisaoRepercussaoGeralController', {
				$scope: scope,
				$stateParams: {resources: [14], task: { id : '1', metadado : { informacao : '1', tipoInformacao: 'PeticaoFisica' }}},
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
		
		it('Deveria carregar informações do processo recursal', function() {
			expect(controller.processo).toEqual(mockProcessoRecursal);
			expect(controller.observacao).toEqual(mockProcessoRecursal.observacaoAnalise);
			expect(controller.teses).toEqual(mockProcessoRecursal.teses);
			expect(controller.assuntos).toEqual(mockProcessoRecursal.assuntos);
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
