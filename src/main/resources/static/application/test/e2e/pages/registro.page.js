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
		
	};

	module.exports = RegistroPage;
	
})();
