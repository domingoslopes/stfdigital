/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 14.12.2015
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
	
	describe('Configurar permissões de usuário', function() {
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como gestor de autuação', function() {
			login('gestor-autuacao');
		});

		it('Deveria navegar para a página de configuração de usuário', function() {
			principalPage = new PrincipalPage();
			expect(browser.isElementPresent(principalPage.conteudo)).toBeTruthy;
			
			// Navega até a página e espera que seja a página correta
			principalPage.iniciarProcesso('link_configurar-permissao');
			expect(browser.getCurrentUrl()).toMatch(/\/permissoes/);
		});
	});
})();