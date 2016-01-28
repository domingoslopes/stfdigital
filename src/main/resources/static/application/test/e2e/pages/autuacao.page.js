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
	
	var AutuacaoPage = function () {
		
		this.partePoloAtivo = function(nome) {
		    element(by.id('partePoloAtivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloAtivo')).click();
		};
		
		this.partePoloPassivo = function(nome) {
		    element(by.id('partePoloPassivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloPassivo')).click();
		};
		
		this.classificar = function(sigla) {
			utils.select('div#s2id_classe', sigla);
		};
		
		this.invalidarPeticaoRadio = function(){
			element(by.css('label[for=no]')).click();
			browser.waitForAngular();
		};
		

		this.preencherMotivoInvalida = function(){
			element(by.model('autuacao.motivo')).sendKeys('Peticao inválida');
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_autuar')).click();
		};
		
	};

	module.exports = AutuacaoPage;
	
})();
