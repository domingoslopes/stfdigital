/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';


	var PreautuacaoPage = function () {
		var preautuacao = this;
		
		preautuacao.classificar = function(sigla){
			var classe =  element(by.css('#s2id_classe a'));
			
			classe.click();
			
			classe.sendKeys(sigla);
			
			var classes = element.all(by.css('.select2-results-dept-0'));
			    
			classes.first().click();
		}
		
		preautuacao.finalizar = function() {
			element(by.id('finalizar')).click();
			
		};
		
	};
	
	

	module.exports = PreautuacaoPage;
	
})();
