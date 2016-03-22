

(function() {
	'use strict'
	
	var LoginPage = require('./pages/login.page');
	var InformacoesUsuarioPage = require('./pages/informacoes-usuario.page');
	var informacoesUsuarioPage;
	var loginPage;
	
	var login = function(user) {
		
		browser.ignoreSynchronization = true;
		
		if (!loginPage) {
			loginPage = new LoginPage();
		}
		
		loginPage.login(user);
		
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
	
	describe('Acessando o sistema como PETICIONADOR', function() {
		
		it('Deve logar como peticionador.', function() {
			login('peticionador');
		});
		
		
		it('Deve ir para a página principal.', function() {
			informacoesUsuarioPage = new InformacoesUsuarioPage();
			informacoesUsuarioPage.exibirInformacoesUsuario();
			expect(browser.getCurrentUrl()).toMatch(/\/usuario/);
		});
	});
})();