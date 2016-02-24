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
		id: 8,
		identificacao: "RE 5",
		numero: 5,
		observacaoAnalise: "123",
		partes: {PoloAtivo: [], PoloPassivo: []},
		pecas: [{id: 1, tipoPecaId : 2, visibilidade: 'P', situacao: 'JUNTADA', descricao: 'peca juntada do processo RE 7'}, 
		        {id: 2, tipoPecaId : 1, visibilidade: 'I', situacao: 'ASSINADA', descricao: 'peca assinada do processo RE 7'}],
		preferencias: [],
		relator: null,
		situacao: "A analisar",
		teses: []
	};
	
	
	describe('Organizar Peças Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;
		var createController;
		var $httpBackend;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $log, messages, properties, $state, ProcessoService) {
			$httpBackend = _$httpBackend_;
			
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/14/processo').respond(mockProcesso);
			
			scope = $rootScope.$new();
			controller = $controller('OrganizaPecasController', {
				$scope: scope,
				$stateParams: {resources: [14]},
				$log: $log,
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
		
		it('Deveria carregar informações do processo', function() {
			expect(controller.processo).toEqual(mockProcesso);
		});
		
		it('Deveria existir peças no processo', function() {
			expect(controller.processo.pecas.length).toBeGreaterThan(0);;
		});
		
		it('Deveria validar o formulário quando os parâmetros não forem alterados', function() {
			expect(controller.validar()).toBeTruthy;
		});
	});
})();