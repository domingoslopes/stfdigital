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
	
	var principalPage;
	
	var pos;
	
	var peticaoId;
	
	describe('Autuação de Petições Digitais Originárias por Órgãos:', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria navegar para a página de envio de petições digitais por órgãos', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			principalPage.login('representante');
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso('link_registrar_peticao_eletronica_orgao');
			
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
			
			peticionamentoPage.removePecas();
			
			peticionamentoPage.uploadPecas();
			
			peticionamentoPage.selecionarTipoPeca('Petição Inicial');
		    
			peticionamentoPage.registrar();

			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			expect(principalPage.dashletMinhasTarefas.count()).toEqual(1);
			
			expect(principalPage.dashletMinhasPeticoes.count()).toEqual(1);
			
			principalPage.login('autuador');
			
		    expect(principalPage.tarefas().count()).toEqual(1);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo #' + peticaoId);
		    });
		    
		});
		

		it('Deveria atuar como válida a petição recebida com órgão', function() {
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/\d+\/autuacao/);
		    
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		    
		});

		it('Deveria distribuir a petição autuada com órgão', function() {
			
		    principalPage.login('distribuidor');
		    
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
			
			distribuicaoPage.selecionar('Min. Roberto Barroso');
			
			//seleciona a primeira parte 
			distribuicaoPage.selecionarPrimeiraParte();
			
			distribuicaoPage.selecionarPrimeiroProcessoDaParte();
			
			distribuicaoPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		}); 

	});
})();
