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
	
	var RevisaoPage = function () {
		
		this.preencherObsAnalise = function(){
			element(by.id('obsAnalise')).sendKeys('observação analise');
		};
		
		this.finalizarRevisao = function() {
			element(by.id('btn_exec_revisar-processo-inapto')).click();
		};
		
	};

	module.exports = RevisaoPage;
	
})();
