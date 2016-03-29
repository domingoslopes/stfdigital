/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 12.02.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var Utils = require('./support');
	
	var utils = new Utils();
	
	var RevisaoRGPage = function () {
		
		this.removerTese = function(){
			element(by.id('btnRemoveTese')).click();
			browser.waitForAngular();
		};
		
		this.selecionarTese = function(motivo){
			element(by.id('btnTeseDropDown')).click();
			element.all(by.repeater('tese in vm.tiposTeses')).
			  get(2).element(by.css('a')).click();
		};
		
		this.preencherNumeroTese = function(numero){
			var inputTese = element(by.id('inputNumeroTese'));
			inputTese.sendKeys(numero, protractor.Key.ENTER);
 		};
		
		this.selecionarAssunto = function(codigoDescricao){
			utils.select('div#s2id_assunto', codigoDescricao);
		};
		
		this.preencherObsAnalise = function(){
			element(by.id('observacao')).sendKeys('observação Analise 2');
		};
		
		this.finalizar = function() {
			var btn = by.id('btn_exec_revisar-repercussao-geral')
			browser.wait(browser.isElementPresent(btn));
			element(btn).click();
		};
		
	};

	module.exports = RevisaoRGPage;
	
})();
