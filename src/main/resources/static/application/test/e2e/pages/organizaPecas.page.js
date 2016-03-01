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
	
	var OrganizaPecasPage = function () {
		
		this.arrastarPecas = function(){
			browser.actions().dragAndDrop(element(by.css("#tabelaPecas tr:nth-child(1)")), element(by.css("#tabelaPecas tr:nth-child(3)"))).mouseDrown().perform();
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_organizar-pecas')).click();
		};
		
	};

	module.exports = OrganizaPecasPage;
	
})();
