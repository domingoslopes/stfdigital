/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var DistribuicaoPage = function () {
		
		this.selecionar = function(ministro) {
			element(by.cssContainingText('option', 'MIN. DIAS TOFFOLI')).click();
		};
		
		this.relator = function() {
			return element(by.model('relator')).$('option:checked').getText();
		};
		
		this.finalizar = function() {
			element(by.id('finalizar')).click();
			
		    browser.waitForAngular();
		};
		
	};

	module.exports = DistribuicaoPage;
	
})();
