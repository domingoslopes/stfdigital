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
		
		this.classificarAptidao = function (apto) {
			if (apto) {
				var off = element(by.css('#classificacao .bootstrap-switch-off'));
				off.isDisplayed()
					.then(function(isVisible) {
						if (isVisible) {
							off.click(); // vai para apto
						} // senão continua no que já está
					});	
			} else {
				var on = element(by.css('#classificacao .bootstrap-switch-on'));
				on.isDisplayed()
					.then(function(isVisible) {
						if (isVisible) {
							on.click(); // vai para inapto
						}
					});	// senão continua no que já está
			}
			
		};
		
		this.preencherObsAnalise = function(){
			element(by.id('obsAnalise')).sendKeys('observação analise');
		};
		
		this.finalizarRevisao = function() {
			element(by.id('btn_exec_revisar-processo-inapto')).click();
		};
		
	};

	module.exports = RevisaoPage;
	
})();
