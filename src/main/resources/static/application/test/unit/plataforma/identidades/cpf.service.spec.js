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
		var cpfService;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function(CPFService) {
			cpfService = CPFService;
		}));

		it('Deveria instanciar o serviço', function() {
			expect(cpfService).not.toEqual(null);
		});

		it('Deveria validar CPFs válidos', function() {
			var cpfValido = "25636816502";
			expect(cpfService.validarCPF(cpfValido)).toBeTruthy();
		});
		

		it('Deveria invalidar CPFs inválidos', function() {
			var cpfInvalido = "11111111111";
			expect(cpfService.validarCPF(cpfInvalido)).toBeFalsy();
		});
	});
})();