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
		
		this.iniciarPesquisa = function() {
			var linkPesquisa = element(by.id('pesquisas'));
			var linkPesquisaPeticoes = element(by.id('pesquisar-peticoes'));
			
			browser.actions().mouseMove(linkPesquisa).perform();
			linkPesquisa.click();
			browser.actions().mouseMove(linkPesquisaPeticoes).perform();
			linkPesquisaPeticoes.click();
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
		
		this.filtrarPorSituacao = function(situacao) {
			utils.select('div#s2id_situacao', situacao);
		};
		
		this.pesquisar = function() {
			element(by.id('botaoPesquisar')).click();
			browser.waitForAngular();
		};
		
		this.resultados = function () {
			return element.all(by.repeater('resultado in resultados'));
		};
		
		this.detalhar = function(index) {
			var row = element.all(by.repeater('resultado in resultados')).get(index);
			row.element(by.css('a[title=Visualizar]')).click();
		};
		
	};

	module.exports = PesquisaPeticaoPage;
	
})();
