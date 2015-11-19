/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 05.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var Utils = require('./support');
	
	var utils = new Utils();
	
	var RegistroPage = function () {
		
		this.classificarTipoRecebimento = function (sigla) {
			utils.select('div#s2id_tipoRecebimento', sigla);
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
			element(by.id('btn_exec_registrar_peticao_fisica')).click();
		};
	};

	module.exports = RegistroPage;
	
})();
