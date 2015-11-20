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
	
	describe('Autuação de Petições Físicas Originárias:', function() {
		
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
			principalPage.iniciarProcesso('link_registrar_peticao_fisica');
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/fisica/);
		});
		
		it('Deveria registrar uma petição física', function(){
			var registroPage = new RegistroPage();
			
			registroPage.qtdVolumes(2);
			
			registroPage.qtdApensos(2);
			
			registroPage.classificarTipoRecebimento('Sedex');
			
			registroPage.numSedex(2);
			
			registroPage.registrar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		    
		});
		
		it('Deveria logar como preautuador', function() {
			login('preautuador');
		});

		it('Deveria pré-atuar como válida a petição recebida', function() {
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Pré-Autuar Processo #' + peticaoId);
		    });
		    
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/\d+\/preautuacao/);
		    
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

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/\d+\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('MS');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    loginPage.logout();
		});
		
		it('Deveria logar como distribuidor', function() {
			login('distribuidor');
		});
		
		it('Deveria distribuir a petição física autuada', function() {
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #' + peticaoId);
		    });
		    
		    
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/\d+\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionarTipoDistribuicao('PREVENCAO');
			
			distribuicaoPage.adicionarProcessoSuggestion('AP 1');
				
			//verifica se a lista de processos preventos possui ao menos um processo
			expect(distribuicaoPage.listaProcessosPreventos().count()).toEqual(1);
			
			distribuicaoPage.criarJustificativa('Teste tipo ditribuicao');
			
			distribuicaoPage.finalizar();
		    
			loginPage.logout();
		}); 
		
	});
})();
