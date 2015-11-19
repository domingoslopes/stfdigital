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
		
		this.classificar = function(sigla) {
			utils.select('div#s2id_classe', sigla);
		};
		
		this.finalizar = function() {
			element(by.id('botaoAutuar')).click();
		};
		
	};

	module.exports = AutuacaoPage;
	
})();
