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
		
	};

	module.exports = DistribuicaoPage;
	
})();
