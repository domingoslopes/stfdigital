/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 05.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var RegistroPage = function () {
		
		this.classificar = function(sigla) {
		    var classe =  element(by.css('#s2id_classe a'));
		    
		    classe.click();
		    
		    classe.sendKeys(sigla);
		    
		    var classes = element.all(by.css('.select2-results-dept-0'));
		    
		    classes.first().click();
		};
		
		this.partePoloAtivo = function(nome) {
		    element(by.id('partePoloAtivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloAtivo')).click();
		}
		
		this.partePoloPassivo = function(nome) {
		    element(by.id('partePoloPassivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloPassivo')).click();
		}
		
		this.registrar = function () {
			element(by.id('botaoPeticionar')).click();
		};
		
	};

	module.exports = RegistroPage;
	
})();
