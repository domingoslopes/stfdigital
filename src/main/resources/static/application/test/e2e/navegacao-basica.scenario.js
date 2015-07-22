/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var DashboardPage = require('./dashboard.page');
	
	var utils = require('./e2e.util');
	
	var dashboardPage;
	
	describe('app', function() {
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
			
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			dashboardPage = new DashboardPage();
		});

		afterEach(function() {
			// Se um dado teste não passou, salva um arquivo com o screenshot dá página atual
			if (!jasmine.getEnv().currentSpec.results().passed()) {
				utils.takeScreenshot(browser, jasmine.getEnv().currentSpec.description.replace(/ /g, '-'));
			}
		});

		it('Deveria carregar o dashboard do usuário', function() {
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(dashboardPage.conteudo)).toBe(true);
		});

		it('Deveria navegar para a página de registro de petições físicas', function() {
			// Iniciando o Processo de Autuação...
			dashboardPage.iniciarProcesso();
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/fisica/);
		});

	});
})();
