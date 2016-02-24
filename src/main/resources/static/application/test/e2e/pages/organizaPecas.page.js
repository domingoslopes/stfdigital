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
		
		this.finalizar = function() {
			element(by.id('btn_exec_organizar-pecas')).click();
		};
		
	};

	module.exports = OrganizaPecasPage;
	
})();
