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
		
		this.classificarTipoRecebimento = function (sigla) {
			var tipoRecebimento =  element(by.css('#s2id_tipoRecebimento a'));
		    
			tipoRecebimento.click();
		    
			tipoRecebimento.sendKeys(sigla);
		    
		    var tipoRecebimentos = element.all(by.css('.select2-results-dept-0'));
		    
		    tipoRecebimentos.first().click();
		};
		
		
		this.qtdVolumes = function(qtd) {
		    element(by.id('qtdVolumes')).sendKeys(qtd);
		};
		
		this.qtdApensos = function(qtd) {
		    element(by.id('qtdApensos')).sendKeys(qtd);
		};
		
		this.numSedex = function(qtd){
			element(by.id('numSedex')).sendKeys(qtd);
		};
		
		this.registrar = function () {
			element(by.id('botaoRegistrar')).click();
		};
	};

	module.exports = RegistroPage;
	
})();
