/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 04.12.2015
 */ 

/* jshint undef:false*/
(function() {
	'use strict';

	describe('Cadastro: Controller', function() {
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, CadastroService) {
			scope = $rootScope.$new();
			
			controller = $controller('CadastroController', {
				$scope : scope,
				CadastroService: CadastroService
			});
		}));

		it('Deveria instanciar o controlador do cadastro', function() {
			expect(controller).not.toEqual(null);
		});
		
		it('Deveria impedir o cadastro sem os campos obrigatórios', function() {
			expect(controller.cadastrarUsuario()).toBeFalsy();
			
			controller.detalhes.login = 'joao.silva';
			expect(controller.cadastrarUsuario()).toBeFalsy();
			
			controller.detalhes.nome = 'João da silva';
			expect(controller.cadastrarUsuario()).toBeFalsy();
				
			controller.detalhes.senha = '123456';
			expect(controller.cadastrarUsuario()).toBeFalsy();
			
			controller.detalhes.senhaRepeticao = '123456';
			expect(controller.cadastrarUsuario()).toBeTruthy();
		});
		
		it('Deveria impedir o cadastro com CPF inválido', function() {
			var cpfValido = "25636816502",
				cpfInvalido = "11111111111";
			
			controller.detalhes = {
				login: 'joao.silva',
				nome: 'João da silva',
				senha: '123456',
				senhaRepeticao: '123456'
			};
			
			controller.detalhes.cpf = cpfInvalido;
			expect(controller.cadastrarUsuario()).toBeFalsy();
			
			controller.detalhes.cpf = cpfValido;
			expect(controller.cadastrarUsuario()).toBeTruthy();
		});
	});
})();
