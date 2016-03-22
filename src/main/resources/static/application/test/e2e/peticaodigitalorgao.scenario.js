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
	
	var PeticionamentoPage = require('./pages/peticionamento.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var LoginPage = require('./pages/login.page');
	
	var principalPage;
	
	var loginPage;
	
	var pos;
	
	var peticaoId;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
		
	describe('Autuação de Petições Digitais Originárias por Órgãos', function() {
		
		it('Deveria logar como representante', function() {
			login('representante');
		});

		it('Deveria navegar para a página de envio de petições digitais por órgãos', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso('link_registrar-peticao-eletronica-orgao');
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/orgao/);
		});

		it('Deveria enviar uma nova petição digital com órgão', function() {
			var peticionamentoPage = new PeticionamentoPage();
			
			peticionamentoPage.classificarClasse('AP');
			
			peticionamentoPage.classificarOrgao('Procuradoria-Geral');
			
			peticionamentoPage.partePoloAtivo('João da Silva');
		    
			peticionamentoPage.partePoloPassivo('Maria da Silva');
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.removePecas();
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Petição Inicial');
		    
			peticionamentoPage.registrar('registrar-peticao-eletronica-orgao');

			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			expect(principalPage.isMensagemSucessoApresentada()).toBeTruthy();
			
		    loginPage.logout();
		});
		
		it('Deveria logar como autuador', function() {
			login('autuador');
		});
		
		it('Deveria atuar como válida a petição recebida com órgão', function() {
			
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo Originário #' + peticaoId);
		    });
			
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    loginPage.logout();
		});
		
		it('Deveria logar como distribuidor', function() {
			login('distribuidor');
		});

		it('Deveria distribuir a petição autuada com órgão', function() {
					    
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length); 
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #' + peticaoId);
		    });
		    
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionarTipoDistribuicao('COMUM');
			
			distribuicaoPage.criarListaDeMinistrosImpedidos();
			
			//verifica se a lista de ministros impedidos possui ao menos um ministro
			expect(distribuicaoPage.listaMinistrosImpedidos().count()).toEqual(1);
			
			distribuicaoPage.criarJustificativa('Teste tipo ditribuicao COMUM');
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		}); 

	});
})();
