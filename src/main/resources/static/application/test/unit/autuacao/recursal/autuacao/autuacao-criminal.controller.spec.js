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
		apensos: 2,
		classe: "RE",
		formaRecebimento: "SEDEX",
		id: 18,
		identificacao: "10/2016",
		numero: 10,
		numeroSedex: "3",
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [],
		preferencias: null,
		processoWorkflowId: 10069,
		tipo: "PeticaoFisica",
		tipoProcesso: "RECURSAL",
		volumes: 1
	};
	
	var mockProcesso = {
		assuntos: [],
		classe: "RE",
		id: 10,
		identificacao: "RE 7",
		numero: 7,
		observacaoAnalise: null,
		partes: {'Polo Ativo': [], 'Polo Passivo': []},
		pecas: [],
		preferencias: [],
		relator: null,
		situacao: "A analisar",
		teses: []
	};
	
	describe('Autuação de Processo Recursal com preferência Criminal/Eleitoral Controller', function() {
		var controller;
		var $httpBackend;
		var properties;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_, ClasseService, PeticaoService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/18/processo').respond(mockProcesso);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/18').respond(mockPeticao);
			
			var scope = $rootScope.$new();
			
			controller = $controller('AutuacaoCriminalController', {
				$scope: scope,
				$stateParams: {resources: [18]},
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

		it('Deveria adicionar partes ao polo ativo', function() {
			var qtdPartesPoloAtivo = controller.partesPoloAtivo.length;
			controller.adicionarPoloAtivo('João');
			controller.adicionarPoloAtivo('Maria');
			expect(controller.partesPoloAtivo.length).toBe(qtdPartesPoloAtivo + 2);
		});

		it('Deveria adicionar partes ao polo passivo', function() {
			var qtdPartesPoloPassivo = controller.partesPoloPassivo.length;
			controller.adicionarPoloPassivo('José');
			controller.adicionarPoloPassivo('Ana');
			expect(controller.partesPoloPassivo.length).toBe(qtdPartesPoloPassivo + 2);
		});
		
		it('Deveria remover partes do polo ativo', function() {
			var qtdPartesPoloAtivo = controller.partesPoloAtivo.length;
			controller.adicionarPoloAtivo('João');
			controller.removerPoloAtivo(0);
			expect(controller.partesPoloAtivo.length).toBe(qtdPartesPoloAtivo);
		});
		
		it('Deveria remover partes do polo passivo', function() {
			var qtdPartesPoloPassivo = controller.partesPoloPassivo.length;
			controller.adicionarPoloPassivo('José');
			controller.removerPoloPassivo(0);
			expect(controller.partesPoloPassivo.length).toBe(qtdPartesPoloPassivo);
		});
		
		it('Deveria adicionar um assunto à lista', function() {
			var qtdAssuntos = controller.assuntosSelecionados.length;
			var assunto = {id: "2662", descricao: "Férias"};
			controller.adicionarAssuntoNaLista(assunto);
			expect(controller.assuntosSelecionados.length).toBe(qtdAssuntos + 1);
		});
		
		it('Deveria não adicionar um assunto repetido à lista', function() {
			var qtdAssuntos = controller.assuntosSelecionados.length;
			var assunto = {id: "2662", descricao: "Férias"};
			controller.adicionarAssuntoNaLista(assunto);
			controller.adicionarAssuntoNaLista(assunto);
			expect(controller.assuntosSelecionados.length).toBe(qtdAssuntos + 1);
		});
		
		it('Deveria remover um assunto da lista', function() {
			var qtdAssuntos = controller.assuntosSelecionados.length;
			var assunto = {id: "2662", descricao: "Férias"};
			controller.adicionarAssuntoNaLista(assunto);
			controller.removerAssuntoSelecionadoLista(0);
			expect(controller.assuntosSelecionados.length).toBe(qtdAssuntos);
		});
		
		it('Deveria não validar a autuação quando não houver partes', function() {
			expect(controller.validar()).toBeFalsy();
		});
		
		it('Deveria não validar a autuação quando não houver partes do polo ativo', function() {
			controller.adicionarPoloPassivo('José');
			expect(controller.validar()).toBeFalsy();
		});
		
		it('Deveria não validar a autuação quando não houver partes do polo passivo', function() {
			controller.adicionarPoloAtivo('João');
			expect(controller.validar()).toBeFalsy();
		});
		
		it('Deveria validar a autuação quando houver partes do polo ativo e passivo', function() {
			controller.adicionarPoloAtivo('João');
			controller.adicionarPoloPassivo('José');
			expect(controller.validar()).toBeTruthy();
		});
	});
})();
