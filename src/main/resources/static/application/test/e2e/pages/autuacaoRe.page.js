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
	
	var AutuacaoRePage = function () {
		
		this.partePoloAtivo = function(nome) {
		    element(by.id('partePoloAtivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloAtivo')).click();
		};
		
		this.partePoloPassivo = function(nome) {
		    element(by.id('partePoloPassivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloPassivo')).click();
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_autuar-recursal')).click();
		};
		
	};

	module.exports = AutuacaoRePage;
	
})();
