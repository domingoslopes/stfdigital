/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var PrincipalPage = require('./pages/principal.page');
	
	var LoginPage = require('./pages/login.page');
	
	var loginPage;
	
	var principalPage;
		
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
		
	describe('Cadastrando associado como GESTOR DE CADASTRO', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como gestor de cadastro', function() {
			login('gestor-cadastro');
		});

		it('Deveria navegar para a página de cadastro de associado', function() {
			principalPage = new PrincipalPage();
			expect(browser.isElementPresent(principalPage.conteudo)).toBeTruthy;
			
			// Navega até a página e espera que seja a página correta
			principalPage.iniciarProcesso('link_registrar-associado');
			expect(browser.getCurrentUrl()).toMatch(/\/associado/);
			loginPage.logout();
		});
	});
		
	describe('Cadastrando associado como GESTOR DE ÓRGÃO', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como gestor de órgão', function() {
			login('gestor-orgao');
		});

		it('Deveria navegar para a página de cadastro de associado', function() {
			principalPage = new PrincipalPage();
			expect(browser.isElementPresent(principalPage.conteudo)).toBeTruthy;
			
			// Navega até a página e espera que seja a página correta
			principalPage.iniciarProcesso('link_registrar-associado');
			expect(browser.getCurrentUrl()).toMatch(/\/associado/);
			loginPage.logout();
		});
	});
})();
