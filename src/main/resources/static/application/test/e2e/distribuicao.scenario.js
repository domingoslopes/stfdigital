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
	
	var RegistroPage = require('./pages/peticionamento.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var principalPage;
	
	describe('Autuação de Originários Distribuídos', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria navegar para a página de envio de petições digitais', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso();
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao/);
		});

		it('Deveria enviar uma nova petição digital', function() {
			var registroPage = new RegistroPage();
			
			registroPage.classificar('AP');
			
			registroPage.partePoloAtivo('João da Silva');
		    
			registroPage.partePoloPassivo('Maria da Silva');
		    
			registroPage.registrar();

			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo #21');
		});

		it('Deveria atuar como válida a petição recebida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/21\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			principalPage.login('distribuidor');
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #26');
		    
		});

		it('Deveria distribuir a petição autuada', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/26\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionar('Min. Roberto Barroso');
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		});

	});
})();
