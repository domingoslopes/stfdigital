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
	
	var RegistroPage = require('./pages/registro.page');
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var principalPage;
	
	describe('Autuação de Originários Distribuídos', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria navegar para a página de registro de petições físicas', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso();
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/fisica/);
		});

		it('Deveria registrar uma nova petição física', function() {
			var registroPage = new RegistroPage();
			
			registroPage.classificar('AP');
			
			registroPage.registrar();

			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Pré-Autuar Processo #7');
		});

		it('Deveria pré-atuar a petição registrada', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/7\/preautuacao/);
		    
			var preautuacaoPage = new PreautuacaoPage();
			
			preautuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			principalPage.login('autuador');
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo #13');
		    
		});

		it('Deveria atuar a petição pré-autuada', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/13\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			principalPage.login('distribuidor');
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #17');
		    
		});

		it('Deveria distribuir a petição autuada como válida', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/17\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionar('MIN. DIAS TOFFOLI');
			
			expect(distribuicaoPage.relator()).toEqual('MIN. DIAS TOFFOLI');
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		});

	});
})();
