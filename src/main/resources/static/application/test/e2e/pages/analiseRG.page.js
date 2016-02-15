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
			element.all(by.repeater('tese in analise.tiposTeses')).
			  get(2).$('a').click()
		};
		
		this.preencherNumeroTese = function(){
			var inputTese = element(by.id('inputNumeroTese'));
			inputTese.sendKeys(1, protractor.Key.ENTER);
 		};
		
		this.selecionarAssunto = function(codigoDescricao){
			utils.select('div#s2id_assunto', codigoDescricao);
		};
		
		this.preencherObsAnalise = function(){
			element(by.id('obsAnalise')).sendKeys('observação analise');
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_analisar-repercussao-geral')).click();
		};
		
	};

	module.exports = AnaliseRGPage;
	
})();
