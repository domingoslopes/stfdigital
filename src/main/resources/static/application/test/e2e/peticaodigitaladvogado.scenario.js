/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var LoginPage = require('./pages/login.page');
	
	var PrincipalPage = require('./pages/principal.page');
	
	var PeticionamentoPage = require('./pages/peticionamento.page');
	
	var RegistroPage = require('./pages/registro.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
	var OrganizaPecasPage = require('./pages/organizaPecas.page');
	
	var principalPage;
	
	var loginPage;
	
	var organizaPecasPage;
	
	var pos;
	
	var peticaoId;
	
	var processoId;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
		
	describe('Autuação de Petições Digitais Originárias:', function() { 
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como peticionador', function() {
			login('peticionador');
		});

		it('Deveria navegar para a página de envio de petições digitais', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso('link_registrar-peticao-eletronica');
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao/);
		});

		it('Deveria enviar uma nova petição digital', function() {
			
			peticionar('RE');
			
			principalPage = new PrincipalPage();
			
			principalPage.iniciarProcesso('link_registrar-peticao-eletronica');
			
			peticionar('AP');
			
			loginPage.logout();
		});
		
		it('Deveria logar como autuador', function() {
			login('autuador');
		});
		
		it('Deveria autuar como válida a petição recebida', function() {
			
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo Originário #' + peticaoId);
		    });
		    
			autuar();
			
			autuar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
		
		it('Deveria logar como distribuidor', function() {
			login('distribuidor');
		});

		it('Deveria distribuir a petição autuada', function() {
					    
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #' + peticaoId);
		    });
		    
		    distribuir('COMUM');
		    
		    distribuir('PREVENCAO');
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
		
		it('Deveria logar como organizador-pecas', function(){
			login('organizador-pecas');
		});
		
		it('Deveria inserir uma peça', function(){
			expect(principalPage.tarefas().count()).toBeGreaterThan(0);
			
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Organizar Peças #' + peticaoId);
		    });
		    
		    principalPage.executarTarefa();
		    
		    inserirPecas();
		});
		
		it ('Deveria alterar o status da ultima peca para juntada', function(){
			organizaPecasPage.executarAcaoJuntar();
		});
		
		it('Deveria editar uma peça', function(){
			editarPeca();
		});
		
		it('Deveria unir as duas primeiras peças', function(){
			organizaPecasPage.executarAcaoUnir(2);
		}); 
		
		it('Deveria dividir uma peça', function(){
			organizaPecasPage.executarAcaoDividir();
			organizaPecasPage.recuperarTotalPaginas().then(function(numeroTotalPaginas){
				if (numeroTotalPaginas > 2){
					organizaPecasPage.selecionarTipoPeca('Documentos comprobatórios');
					organizaPecasPage.setarDescricao('Descricao primeira peça');
					organizaPecasPage.setarPaginaInicialFinal(1, 2);
					organizaPecasPage.adicionarPeca();
					organizaPecasPage.selecionarTipoPeca('Peticão Inicial');
					organizaPecasPage.setarDescricao('Descricao segunda peça');
					organizaPecasPage.setarPaginaInicialFinal(2, numeroTotalPaginas);
					organizaPecasPage.adicionarPeca();
					organizaPecasPage.confirmarAcaoDividir();
				}
			});
		});
		
		it ('Deveria excluir uma peça', function(){
			organizaPecasPage.executarAcaoExcluir();
			loginPage.logout();
		});
				
		it('Deveria logar como gestor-autuacao', function() {
			login('gestor-autuacao');
		});
		
		it('Deveria exibir os dashlets do papel gestor-autuacao', function(){	
			expect(principalPage.dashletGestaoAutuacao.isDisplayed()).toBe(true)
			loginPage.logout();
		});
		
		it('Deveria logar como cartoraria', function() {
			login('cartoraria');
		});
		
		it ('Deveria exibir a dashlet do papel cartorária', function() {
			expect(principalPage.dashletMinhasTarefas.isDisplayed()).toBe(true);
			loginPage.logout();
		});
		
		var editarPeca = function() {
			if (!organizaPecasPage) {
		    	organizaPecasPage = new OrganizaPecasPage();
		    }
			
			organizaPecasPage.executarAcaoEditarPeca();
			organizaPecasPage.alteraNumeroOrdemPeca('2');
			organizaPecasPage.alteraTipoPeca('Ato coator');
			organizaPecasPage.alteraDescricaoPeca('Peça alterada');
			organizaPecasPage.alteraVisibilidadePeca('Pendente de visualização');
			organizaPecasPage.salvarEdicaoPeca();
		}
		
		var inserirPecas = function(){
			if (!organizaPecasPage) {
		    	organizaPecasPage = new OrganizaPecasPage();
		    }
		    
			//Acessa a página de inserção de peças.
			organizaPecasPage.acionarOpcaoInserirPecas();
			
			//Faz o upload de uma peça.
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			
			//Remove a peça.
			organizaPecasPage.removerPeca();
			
			//Insere duas peças.
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			
			//Exclui as duas peças.
			organizaPecasPage.removerTodasPecas();
			
			//Faz o upload de uma peça.
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			
			//Edita os campos da peça inserida.
			organizaPecasPage.setarDescricao('Nova peça');
			//organizaPecasPage.selecionarTipoPeca('Documentos Comprobatórios');
			organizaPecasPage.selecionarVisibilidadePeca('Pendente de visualização');
			
			organizaPecasPage.executarInsercaoPecas();
		}
		
		var peticionar = function(siglaClasse){
			
			var peticionamentoPage = new PeticionamentoPage();
			
			peticionamentoPage.classificarClasse(siglaClasse);
			
			peticionamentoPage.partePoloAtivo('João da Silva');
		    
			peticionamentoPage.partePoloPassivo('Maria da Silva');
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.removePecas();
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Ato coator');
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Documentos Comprobatórios', 1);
			
			peticionamentoPage.registrar('registrar-peticao-eletronica');
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			expect(principalPage.isMensagemSucessoApresentada()).toBeTruthy();
		}
		
		var autuar = function(){

			principalPage.executarTarefa();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/autuacao/);
			
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.partePoloAtivo('João da Silva');
		    
			autuacaoPage.partePoloPassivo('Maria da Silva');
			
			autuacaoPage.finalizar();
		}
		
		var distribuir = function(tipoDistribuicao){
			
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionarTipoDistribuicao(tipoDistribuicao);
			
			if (tipoDistribuicao == 'COMUM') {
				
				distribuicaoPage.criarListaDeMinistrosImpedidos();
				
				//verifica se a lista de ministros impedidos possui ao menos um ministro
				expect(distribuicaoPage.listaMinistrosImpedidos().count()).toBeGreaterThan(0);
				
			} else if (tipoDistribuicao == 'PREVENCAO') {
				
				distribuicaoPage.selecionarPrimeiraParte();
				
				distribuicaoPage.selecionarPrimeiroProcessoDaParte();
				
				//verifica se a lista de processos preventos possui ao menos um processo
				expect(distribuicaoPage.listaProcessosPreventos().count()).toBeGreaterThan(0);
			}
			
			distribuicaoPage.criarJustificativa('Teste tipo ditribuicao ' + tipoDistribuicao);
			
			distribuicaoPage.finalizar();
		}
		
		var inserirPecas = function(){
			if (!organizaPecasPage) {
		    	organizaPecasPage = new OrganizaPecasPage();
		    }
		    
			//Acessa a página de inserção de peças.
			organizaPecasPage.acionarOpcaoInserirPecas();
			
			//Faz o upload de uma peça.
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			
			//Remove a peça.
			organizaPecasPage.removerPeca();
			
			//Insere duas peças.
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);

			//Exclui as duas peças.
			organizaPecasPage.removerTodasPecas();
			
			//Faz o upload de uma peça.
			organizaPecasPage.uploadPecas();
			organizaPecasPage.waitUploadFinished(0);
			organizaPecasPage.setarDescricao('Teste')
			organizaPecasPage.selecionarTipoPeca('Ato coator');
			organizaPecasPage.selecionarVisibilidadePeca('Público')
			
			organizaPecasPage.executarInsercaoPecas();
		}
	});
})();