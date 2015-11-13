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
		
		this.finalizar = function() {
			element(by.id('finalizar')).click();
			
		    browser.waitForAngular();
		};
		
		this.selecionarPrimeiraParte = function(){
			element(by.repeater('parte in partes').row(0)).element(by.css('a')).click();
		};
		
		this.selecionarPrimeiroProcessoDaParte = function(){
			element(by.repeater('processo in processosParte').row(0)).element(by.css('a')).click();
		};
		
	};

	module.exports = DistribuicaoPage;
	
})();
