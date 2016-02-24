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
		id: 10,
		identificacao: "RE 7",
		numero: 7,
		observacaoAnalise: null,
		partes: {'Polo Ativo': [], 'Polo Passivo': []},
		pecas: [{id: 1, tipoPecaId : 2, visibilidade: 'P', situacao: 'JUNTADA', descricao: 'peca juntada do processo RE 7'}, 
		        {id: 2, tipoPecaId : 1, visibilidade: 'I', situacao: 'ASSINADA', descricao: 'peca assinada do processo RE 7'}];
		teses: []
	};
	
	describe('Controle das peças do processo selecionado', function() {
		var controller;
		var $httpBackend;
		var properties;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_, ClasseService, PeticaoService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/processo/1/pecas').respond(mockProcesso);
			
			var scope = $rootScope.$new();
			
			controller = $controller('OrganizaPecasController', {
				$scope: scope,
				$stateParams: {resources: [18]},
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

		
		it('Deveria recuperar as pecas do processo', function() {
			var pecas = [];
			pecas = controller.processo.pecas;
			expect(pecas.length).toBeGreaterThan(0);
		});
		
		/*it('Deveria não adicionar um assunto repetido à lista', function() {
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
		});*/
	});
})();
