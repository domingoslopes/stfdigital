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
		
		var idDoUsuarioCadastrado = 30; // Número aleatório

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
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, properties, CadastroService) {
			$httpBackend.expectPOST(
				properties.apiUrl + '/acessos/usuarios', 
				jasmine.objectContaining(detalhesValidos)
			).respond(idDoUsuarioCadastrado);

			$httpBackend.expectPOST(
				properties.apiUrl + '/acessos/usuarios', 
				jasmine.objectContaining(detalhesInvalidos)
			).respond({error: 'Erro simulado'});

			cadastroService = CadastroService;
			scope = $rootScope.$new();
		}));
		
		it("Deveria cadastrar um usuário", function() {
			cadastroService.cadastrar(detalhesValidos).then(function(response) {
				expect(response).toEqual(jasmine.any(Number));
			});
		});
		
		it("Deveria rejeitar um usuário com informações insuficientes", function() {
			cadastroService.cadastrar(detalhesInvalidos).then(function(response) {
				expect(response).not.toEqual(jasmine.any(Number));
			})
		});
	});
})();