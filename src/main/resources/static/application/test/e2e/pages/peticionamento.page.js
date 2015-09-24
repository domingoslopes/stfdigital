/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 05.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var PeticionamentoPage = function () {
		
		this.classificar = function(sigla) {
			utils.select('div#s2id_classe', sigla);
		};
		
		this.partePoloAtivo = function(nome) {
		    element(by.id('partePoloAtivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloAtivo')).click();
		};
		
		this.partePoloPassivo = function(nome) {
		    element(by.id('partePoloPassivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloPassivo')).click();
		};
		
		this.registrar = function () {
			element(by.id('botaoPeticionar')).click();
		};
		
	};

	module.exports = PeticionamentoPage;
	
})();
