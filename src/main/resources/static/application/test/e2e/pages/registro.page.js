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
		this.classe = element(by.model('registro.tipoRecebimento'));
		
		this.classificar = function (classe) {
			this.classe.sendKeys(classe);
		};
		
		this.registrar = function () {
			element(by.id('botaoRegistrar')).click();
		};
		
		this.qtdVolume = function(qtd) {
		    element(by.id('qtdVolumes')).sendKeys(qtd);
		};
		
		this.qtdApensos = function(qtd) {
		    element(by.id('qtdApensos')).sendKeys(qtd);
		};
		
		this.registrar = function () {
			element(by.id('botaoReceber')).click();
		};
		
	};

	module.exports = RegistroPage;
	
})();
