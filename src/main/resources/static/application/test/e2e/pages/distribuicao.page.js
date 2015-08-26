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
		
		this.selecionar = function(nome) {
		    var relator =  element(by.css('#s2id_relator a'));
		    
		    relator.click();
		    
		    relator.sendKeys(nome);
		    
		    var ministros = element.all(by.css('.select2-results-dept-0'));
		    
		    ministros.first().click();
		};
		
		this.finalizar = function() {
			element(by.id('finalizar')).click();
			
		    browser.waitForAngular();
		};
		
	};

	module.exports = DistribuicaoPage;
	
})();
