/**
 * Realiza operações que resultam na inclusão de vários documentos.
 * 
 * @author Tomas.Godoi
 * @since 2016-01-19
 */
/* jshint undef:false */
(function() {
	'use strict';

	var externalDir = '../e2e';

	var LoginPage = require(externalDir + '/pages/login.page');

	var PrincipalPage = require(externalDir + '/pages/principal.page');

	var PesquisaPeticaoPage = require(externalDir + '/pages/pesquisapeticao.page');
	
	var DetalhePeticaoPage = require(externalDir + '/pages/detalhepeticao.page');
	
	var pesquisaPeticaoPage;

	var principalPage;

	var loginPage;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage)
			loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};

	describe('Benchmark Pesquisar Petição-Preparação:', function() {

		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria logar como peticionador', function() {
			login('peticionador');
		});

		it('Deveria navegar para a página de pesquisa', function() {
			pesquisaPeticaoPage = new PesquisaPeticaoPage();
			pesquisaPeticaoPage.iniciarPesquisa();
			expect(browser.getCurrentUrl()).toMatch(/\/pesquisa\/peticoes/);
		});

		it('Deveria pesquisar uma petição', function(done) {
			var anoAtual = new Date().getFullYear();
			pesquisaPeticaoPage.filtrarPorAno(anoAtual);
			pesquisaPeticaoPage.pesquisar();
			
			expect(pesquisaPeticaoPage.resultados().count()).toBeGreaterThan(0);
			
			pesquisaPeticaoPage.resultados().count().then(function(count) {
				runBenchmark(count);
				done();
			});
		});
		
		it('Deveriar recarregar a página.', function() {
			browser.refresh();
		});
		
	});
	
	var runBenchmark = function(total) {
		for (var k = 0; k < total; k++) {
			(function(index) {
				describe('Benchmark Pesquisar Petição-Execução-' + (index + 1) + '/' + total + ':' , function() {
					
					beforeEach(function() {
						console.info('\nrodando: Benchmark: ' + (index + 1) + '/' + total + ':', jasmine.getEnv().currentSpec.description);
					});
					
					it('Deveria navegar para a página de pesquisa', function() {
						pesquisaPeticaoPage = new PesquisaPeticaoPage();
						pesquisaPeticaoPage.iniciarPesquisa();
						expect(browser.getCurrentUrl()).toMatch(/\/pesquisa\/peticoes/);
					});
		
					it('Deveria pesquisar uma petição', function() {
						var anoAtual = new Date().getFullYear();
						pesquisaPeticaoPage.filtrarPorAno(anoAtual);
						pesquisaPeticaoPage.pesquisar();
		
						expect(pesquisaPeticaoPage.resultados().count()).toBeGreaterThan(0);
						
						pesquisaPeticaoPage.detalhar(index);
						
						var detalhePeticaoPage = new DetalhePeticaoPage();
						
						detalhePeticaoPage.linhasPecas().count().then(function(count) {
							for (var i = 0; i < count; i++) {
								browser.controlFlow().execute(function(pecaIndex) {
									return function() {
										detalhePeticaoPage.visualizarPeca(pecaIndex);
									}
								}(i));
							}
						});
						
					});
				});
			})(k);
		}
		
		describe('Benchmark Pesquisar Petição-Depois:', function() {
			
			beforeEach(function() {
				console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
			});
			
			it('Deveria fazer logout', function() {
				loginPage.logout();
			});

		});
		
	};
	
})();