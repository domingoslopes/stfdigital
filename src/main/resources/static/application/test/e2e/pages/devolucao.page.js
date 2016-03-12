/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 24.11.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var DevolucaoPage = function () {
		var devolucao = this;
		
		devolucao.classificar = function(tipo){
			utils.select('div#s2id_tipoDevolucao', tipo);
		};
		
		devolucao.registrarNumeroOficio = function(){
			element(by.id('numero')).sendKeys(1000);
		};
		
		devolucao.aguardarTagsCarregadas = function() {
			browser.wait(element(by.id('painel-tags')).isDisplayed);
		};
		
		devolucao.gerarTexto = function() {
			element(by.id('botao-gerar-texto')).click();
			browser.wait(element(by.id('painel-editor')).isDisplayed);
		};
		
		devolucao.finalizar = function() {
			element(by.id('btn_exec_devolver-peticao')).click();
		};
	};

	module.exports = DevolucaoPage;
	
})();