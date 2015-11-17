/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var PesquisaPeticaoPage = require('./pages/pesquisapeticao.page');
	var LoginPage = require('./pages/login.page');
	var pesquisaPeticaoPage;
	var loginPage;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
	
	describe('Pesquisa de petição:', function() {
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como peticionador', function() {
			login('peticionador');
		});

		it('Deveria navegar para a página de pesquisa', function() {
			pesquisaPeticaoPage = new PesquisaPeticaoPage();
			pesquisaPeticaoPage.iniciarPesquisa();
			expect(browser.getCurrentUrl()).toMatch(/\/pesquisa\/peticao/);
		});
		
		it('Deveria pesquisar uma petição', function(){
			//pesquisaPeticaoPage.filtrarPorNumero(22);
			pesquisaPeticaoPage.filtrarPorAno(2015);
			pesquisaPeticaoPage.filtrarPorClasse('AP');
			pesquisaPeticaoPage.filtrarPorParte('Maria da Silva');
			pesquisaPeticaoPage.pesquisar();
			
		    expect(pesquisaPeticaoPage.resultados().count()).toBeGreaterThan(0);    
		    expect(pesquisaPeticaoPage.resultados().get(0).getText()).toMatch('/2015 AP');
		    loginPage.logout();
		});	
	});
})();
