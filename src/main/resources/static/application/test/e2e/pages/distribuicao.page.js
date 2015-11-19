/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var DistribuicaoPage = function () {
		
		this.selecionar = function(nome) {
			utils.select('div#s2id_relator', nome);
		};
		
		this.selecionarTipoDistribuicao = function(nome) {
			utils.select('div#s2id_tipoDistribuicaoId', nome);
			browser.waitForAngular()
		};
		
		this.criarListaDeMinistrosImpedidos = function(){
			 
			var allOptions = element.all(by.options('r as display(r.data) for r in leftListRows'));
			var firstOption = allOptions.first();
			
			firstOption.click();
			browser.waitForAngular();
			element(by.css('[ng-click="moveRightSelected()"]')).click();
		};
		
		this.listaMinistrosImpedidos = function() {
			return element.all(by.options('r as display(r.data) for r in rightListRows'));
		};
		
		this.criarJustificativa = function(texto){
			element(by.model('justificativa')).sendKeys(texto);
		};
		
		this.selecionarPrimeiraParte = function(){
			element.all(by.repeater('parte in partes')).get(0).element(by.css('a[ng-click="consultarProcesso(parte)"]')).click();
		};
		
		this.selecionarPrimeiroProcessoDaParte = function(){
			element.all(by.repeater('processoP in parte.processosParte')).get(0).element(by.css('a[ng-click="adicionarProcessoPreventoLista(processoP)"]')).click();
		};
		
		this.listaProcessosPreventos = function(){
			return element.all(by.repeater('processo in processosPreventos'));
		};
		
		this.adicionarProcessoSuggestion = function(siglaNumeroProcesso){
			element(by.id('processo')).sendKeys(siglaNumeroProcesso);
		}
		
		this.finalizar = function() {
			element(by.id('finalizar')).click();
			
		    browser.waitForAngular();
		};
		
		
		
	};

	module.exports = DistribuicaoPage;
	
})();
