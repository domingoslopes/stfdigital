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
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
	var DevolucaoPage = require('./pages/devolucao.page')
	
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
	
	describe('Autuação de Petições Físicas Originárias (teste do fluxo de peticao devolvida):', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
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
		
		it('Deveria registrar uma petição física (fluxo de peticao devolvida', function(){
			var registroPage = new RegistroPage();
			
			registroPage.preencherQtdVolumes(10);
			
			registroPage.preencherQtdApensos(10);
			
			registroPage.classificarTipoRecebimento('Sedex');
			
			registroPage.preencherNumeroSedex(10);
			
			registroPage.registrar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		    
		});
		
		it('Deveria logar como preautuador', function() {
			login('preautuador');
		});

		it('Deveria pré-atuar como INDEVIDA a petição recebida', function() {
			
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Pré-Autuar Processo #' + peticaoId);
		    });
		    
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/preautuacao/);
		    
			var preautuacaoPage = new PreautuacaoPage();
			
			preautuacaoPage.classificar('AP');
			
			//seta a peticao como indevida
			preautuacaoPage.invalidarPeticaoRadio()
			
			preautuacaoPage.preencherMotivoIndevida();
			
			preautuacaoPage.finalizar();
			
			loginPage.logout();
		    
		});
		
		it('Deveria logar como cartoraria', function() {
			login('cartoraria');
		});
		
		it('Deveria registrar a motivação da devolução', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/devolucao/);
		    
			var devolucaoPage = new DevolucaoPage();
			
			devolucaoPage.classificar('Transitado');
			
			devolucaoPage.registrarNumeroOficio();
			
			devolucaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/visualizacao\/peticao\/\d+/);
			
		    loginPage.logout();
		});
		
		it('Deveria logar como gestor-recebimento', function() {
			login('gestor-recebimento');
		});
		
		it('Deveria assinar o documento de devolução.', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/assinatura-devolucao/);
		    
			
			
		    loginPage.logout();
		});
		
	});
})();
