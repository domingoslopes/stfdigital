/**
 * @author Rodrigo.barreiros
 * 
 * @since 04.04.2016
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var PrincipalPage = require('./pages/principal.page');
	
	var EnvioPage = require('./pages/envio.page')
	
	var LoginPage = require('./pages/login.page');
	
	var principalPage;
    
    var envioPage;
	
	var loginPage;
	
	var pos;
	
	var processoId;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
	
	describe('Envio de processos', function() {
		
		it('Deveria logar como representante do tribunal', function() {
			login('representante-tribunal');
		});
		
		it('Deveria navegar para a página de envio de petições físicas', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
		    envioPage = new EnvioPage();
			
			// Iniciando o Processo de Envio
			principalPage.iniciarProcesso('link_consultar-processo-envio');
            
            envioPage.clicaBotaoEnvioProcesso();
			
			// Verificando se, após iniciar o processo, o browser está na página de envio de processos
			expect(browser.getCurrentUrl()).toMatch(/\/processo\/envio\/novo/);
		});

		
		it('Deveria preencher as informações parcialmente e salvar', function(){
			
			envioPage.classificarClasse('RE');
			
			envioPage.classificarSigilo('Publico');
			
			envioPage.selecionarPreferencias('Criminal');
            
            envioPage.salvarProcesso();
        
            expect(browser.getCurrentUrl()).toMatch(/\/processo\/envio\/consulta/);
            
        });   
        
        it('Deveria preencher o restante das informações e enviar', function(){ 
            
            envioPage.selecionarPrimeiroProcesso();
            
            envioPage.selecionarTarefaEnviaProcesso();
			
			envioPage.classificarProcedencia('Acre');
			
			envioPage.classificarTribunalJuizo('Juiz Eleitoral');
			
			envioPage.preencherNumeroOrigem();
			
			envioPage.classificarAssuntos('direito sindical');
            
            envioPage.adicionarPoloAtivo('Maria da Silva');
            
            envioPage.setaPoloPassivo();
            
            envioPage.adicionarPoloPassivo('Mario Lopes');
            
            envioPage.finalizarEnvio();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		    
		});
		
	});
})();
