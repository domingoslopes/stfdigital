/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 11.12.2015
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
	
	var gerarCPFAleatorio = function() {
		var cpf = "";
		var soma = 0;
		var resto;
		
		for (var i = 0; i < 9; i++) {
			var n = Math.floor(9 * Math.random());
			cpf += n;
			soma += (10 - i) * n;
		}
		
		resto = (10 * soma) % 11;
		if (resto > 9) {
			resto = 0;
		}
		
		cpf += resto;
		
		soma = 0;
		for (var i = 0; i < 10; i++) {
			var n = parseInt(cpf.substr(i, 1));
			soma += (11 - i) * n; 
		}
		
		resto = (10 * soma) % 11;
		if (resto > 9) {
			resto = 0;
		}
		
		cpf += resto;
		
		return cpf;
	}
		
	describe('Cadastrando associado como GESTOR DE CADASTRO', function() {
		
		principalPage = new PrincipalPage();
		cadastroAssociado = new CadastroAssociado();
				
		it('Deveria logar como gestor de cadastro', function() {
			login('gestor-cadastro');
		});

		it('Deveria navegar para a página de cadastro de associado', function() {
			expect(browser.isElementPresent(principalPage.conteudo)).toBeTruthy();
			
			// Navega até a página e espera que seja a página correta
			principalPage.iniciarProcesso('link_registrar-associado');
			expect(browser.getCurrentUrl()).toMatch(/\/associado/);
		});
		
		it('Deveria enviar o formulário com todos os dados', function() {
			// Espera até que o formulário esteja presente
			browser.wait(function() {
			    return $('#nome').isPresent();
			});
			
			cadastroAssociado.nome("João da Silva");
			cadastroAssociado.cpf(gerarCPFAleatorio());
			cadastroAssociado.cargo("Servidor");
			cadastroAssociado.tipoAssociacao("Associado");
			cadastroAssociado.orgao("DEFENSORIA PÚBLICA DA UNIÃO");
			cadastroAssociado.enviar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
	});
		
	xdescribe('Cadastrando associado como GESTOR DE ÓRGÃO', function() {
		
		principalPage = new PrincipalPage();
		cadastroAssociado = new CadastroAssociado();
		
		it('Deveria logar como gestor de órgão', function() {
			login('gestor-orgao');
		});

		it('Deveria navegar para a página de cadastro de associado', function() {
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
			
			cadastroAssociado.nome("João da Silva");
			cadastroAssociado.cpf(gerarCPFAleatorio());
			cadastroAssociado.cargo("Servidor");
			cadastroAssociado.tipoAssociacao("Representante");
			cadastroAssociado.orgao("PROCURADORIA-GERAL DA REPÚBLICA");
			cadastroAssociado.enviar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
	});
})();
