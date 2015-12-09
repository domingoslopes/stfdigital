/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 04.12.2015
 */ 

/* jshint undef:false*/
(function() {
	'use strict';

	describe('Cadastro: Service', function() {
		var controller;
		var scope;
		var cadastroService;
		
		var detalhesValidos = {
			login: 'joao.silva',
			nome: 'João da Silva',
			email: 'joao.silva@exemplo.com.br',
			cpf: '68748373702',
			telefone: '',
			senha: '123456'
		};

		var detalhesInvalidos = {
			nome: 'João da Silva',
			email: 'joao.silva@exemplo.com.br',
			cpf: '68748373702',
			telefone: '',
			senha: '123456'
		};
		
		var respostaValida = {
			id: jasmine.any(Number),
			login: 'joao.silva',
			nome: 'João da Silva',
			email: 'joao.silva@exemplo.com.br',
			cpf: '68748373702',
			telefone: '',
		};
		
		var respostaInvalida = {error: 'Erro simulado'};
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, properties, CadastroService) {
			$httpBackend.expectPOST(
				properties.apiUrl + '/acessos/usuarios', 
				jasmine.objectContaining(detalhesValidos)
			).respond(respostaValida);

			$httpBackend.expectPOST(
				properties.apiUrl + '/acessos/usuarios', 
				jasmine.objectContaining(detalhesInvalidos)
			).respond(respostaInvalida);

			cadastroService = CadastroService;
			scope = $rootScope.$new();
		}));
		
		it("Deveria cadastrar um usuário", function() {
			cadastroService.cadastrar(detalhesValidos).then(function(response) {
				expect(response).toEqual(jasmine.objectContaining(respostaValida));
			});
		});
		
		it("Deveria rejeitar um usuário com informações insuficientes", function() {
			cadastroService.cadastrar(detalhesInvalidos).then(function(response) {
				expect(response).not.toEqual(jasmine.objectContaining(respostaInvalida));
			});
		});
	});
})();