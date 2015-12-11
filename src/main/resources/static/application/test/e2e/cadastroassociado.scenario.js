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
	
	var CadastroAssociado = require('./pages/cadastro-associado.page');
	
	var loginPage;
	
	var principalPage;
	
	var cadastroAssociado;
		
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
		
	xdescribe('Cadastrando associado como GESTOR DE CADASTRO', function() {
		
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
		});
		
		it('Deveria enviar o formulário com todos os dados', function() {
			// Espera até que o formulário esteja presente
			browser.wait(function() {
			    return $('#nome').isPresent();
			});
			
			cadastroAssociado = new CadastroAssociado();
			cadastroAssociado.nome("João da Silva");
			cadastroAssociado.cpf("04102030255"); // TODO: Gabriel.Bastos Gerar CPF válido, pra evitar o erro de "Associado já cadastrado com esse CPF"
			cadastroAssociado.cargo("Servidor");
			cadastroAssociado.tipoAssociacao("Representante");
			cadastroAssociado.orgao("DEFENSORIA PÚBLICA DA UNIÃO");
			cadastroAssociado.enviar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
	});
		
	xdescribe('Cadastrando associado como GESTOR DE ÓRGÃO', function() {
		
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
		});
		
		it('Deveria enviar o formulário com todos os dados', function() {
			// Espera até que o formulário esteja presente
			browser.wait(function() {
			    return $('#nome').isPresent();
			});
			
			cadastroAssociado = new CadastroAssociado();
			cadastroAssociado.nome("João da Silva");
			cadastroAssociado.cpf("73880063540"); // TODO: Gabriel.Bastos Gerar CPF válido, pra evitar o erro de "Associado já cadastrado com esse CPF"
			cadastroAssociado.cargo("Servidor");
			cadastroAssociado.tipoAssociacao("Representante");
			cadastroAssociado.orgao("PROCURADORIA-GERAL DA REPÚBLICA");
			cadastroAssociado.enviar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
	});
})();
