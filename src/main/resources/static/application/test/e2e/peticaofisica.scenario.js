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
	
	var RegistroPage = require('./pages/registro.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
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
	
	describe('Autuação de Petições Físicas Originárias', function() {

		it('Deveria logar como recebedor', function() {
			login('recebedor');
		});

		it('Deveria navegar para a página de envio de petições físicas', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Iniciando o Processo de Remessa Físca
			principalPage.iniciarProcesso('link_registrar-peticao-fisica');
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/fisica/);
		});
		
		it('Deveria registrar uma petição física', function(){
			var registroPage = new RegistroPage();
			
			registroPage.preencherQtdVolumes(2);
			
			registroPage.preencherQtdApensos(2);
			
			registroPage.classificarTipoRecebimento('Sedex');
			
			registroPage.preencherNumeroSedex(2);
			
			registroPage.selecionarTipoProcesso('Originário');
			
			registroPage.registrar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		    
		});
		
		it('Deveria logar como preautuador originário', function() {
			login('preautuador-originario');
		});

		it('Deveria pré-atuar como válida a petição recebida', function() {
			
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Pré-Autuar Processo Originário #' + peticaoId);
		    });
		    
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/preautuacao/);
		    
			var preautuacaoPage = new PreautuacaoPage();
			
			preautuacaoPage.classificar('AP');
			
			preautuacaoPage.finalizar();
			
			loginPage.logout();
		    
		});
		
		it('Deveria logar como autuador', function() {
			login('autuador');
		});
		
		it('Deveria atuar como válida a petição física recebida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.partePoloAtivo('Maria');
			
			autuacaoPage.partePoloPassivo('Joana');
			
			autuacaoPage.classificar('MS');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    loginPage.logout();
		});
		
		it('Deveria logar como distribuidor', function() {
			login('distribuidor');
		});
		
		it('Deveria distribuir a petição física autuada', function() {
			
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
		    
			loginPage.logout();
		}); 
		
	});
})();
