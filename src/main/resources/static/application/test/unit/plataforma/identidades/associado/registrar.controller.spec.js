/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 04.12.2015
 */ 

/* jshint undef:false*/
(function() {
	'use strict';

	describe('Registro de Associado: Controller', function() {
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, AssociadoService, CPFService, SecurityService) {
			scope = $rootScope.$new();
			
			controller = $controller('RegistrarAssociadoController', {
				$scope : scope,
				AssociadoService: AssociadoService,
				CPFService: CPFService,
				SecurityService: SecurityService
			});
		}));

		it('Deveria instanciar o controlador do cadastro de associado', function() {
			expect(controller).not.toEqual(null);
		});
		
		it('Deveria impedir o cadastro sem os campos obrigatórios', function() {
			var cpfValido = "25636816502";
			var cpfInvalido = "11111111111";
			
			expect(controller.cadastrar()).toBeFalsy();
			
			controller.detalhes.idOrgao = "1";
			expect(controller.cadastrar()).toBeFalsy();
			
			controller.detalhes.cpf = cpfValido;
			expect(controller.cadastrar()).toBeFalsy();
			
			controller.detalhes.nome = 'João da silva';
			expect(controller.cadastrar()).toBeTruthy();
				
			controller.detalhes.tipoAssociacao = 'Gestor';
			expect(controller.cadastrar()).toBeTruthy();
		});
		
		it('Deveria impedir o cadastro com CPF inválido', function() {
			var cpfValido = "25636816502",
				cpfInvalido = "11111111111";
			
			controller.detalhes = {
				idOrgao: "1",
				nome: "João da Silva",
				tipoAssociacao: "Associado"
			};
			
			controller.detalhes.cpf = cpfInvalido;
			expect(controller.cadastrar()).toBeFalsy();
			
			controller.detalhes.cpf = cpfValido;
			expect(controller.cadastrar()).toBeTruthy();
		});
	});
})();
