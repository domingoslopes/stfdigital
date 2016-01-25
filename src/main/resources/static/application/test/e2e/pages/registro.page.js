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
		
		this.preencherQtdVolumes = function(qtd) {
		    element(by.id('qtdVolumes')).sendKeys(qtd);
		};
		
		this.preencherQtdApensos = function(qtd) {
		    element(by.id('qtdApensos')).sendKeys(qtd);
		};
		
		this.preencherNumeroSedex = function(qtd){
			element(by.id('numSedex')).sendKeys(qtd);
		};
		
		this.selecionarTipoProcesso = function (tipo) {
			utils.select('div#s2id_tipoProcesso', tipo);
		};
		
		this.registrar = function () {
			element(by.id('btn_exec_registrar-peticao-fisica')).click();
		};
	};

	module.exports = RegistroPage;
	
})();
