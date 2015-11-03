/**
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var PesquisaPeticaoPage = function () {
		browser.get('/');
				
		this.iniciarPesquisa = function() {
			var linkPesquisa = element(by.id('pesquisas'));
			var linkPesquisaPeticao = element(by.id('pesquisa_peticao'));
			
			browser.actions().mouseMove(linkPesquisa).perform();
			linkPesquisa.click();
			browser.actions().mouseMove(linkPesquisaPeticao).perform();
			linkPesquisaPeticao.click();
		};
		
		this.filtrarPorNumero = function(numero) {
		    element(by.id('numero')).sendKeys(numero);
		};
		
		this.filtrarPorAno = function(ano) {
		    element(by.id('ano')).sendKeys(ano);
		};
		
		this.filtrarPorClasse = function(classe) {
			utils.select('div#s2id_classe', classe);
		};
		
		this.filtrarPorParte = function(nome) {
			utils.select('div#s2id_pessoa', nome);
		};
		
		this.pesquisar = function() {
			element(by.id('botaoPesquisar')).click();
			browser.waitForAngular();
		};
		
		this.resultados = function () {
			return element.all(by.repeater('resultado in resultados'));
		};
		
	};

	module.exports = PesquisaPeticaoPage;
	
})();
