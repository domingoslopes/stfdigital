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
	
	var PrincipalPage = require('./principal.page');
	
	var utils = new Utils();
	
	var AnaliseRGPage = function () {
		
		this.selecionarTese = function(motivo){
			element(by.id('btnTeseDropDown')).click();
			element.all(by.repeater('tese in analise.tiposTeses')).
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
			element(by.id('observacao')).sendKeys('observação analise');
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_analisar-repercussao-geral')).click();
		};
		
	};

	module.exports = AnaliseRGPage;
	
})();
