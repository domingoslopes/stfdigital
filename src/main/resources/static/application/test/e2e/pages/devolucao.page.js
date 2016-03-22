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
		
		devolucao.classificarMotivo = function(tipo){
			utils.select('div#s2id_motivoDevolucao', tipo);
			browser.waitForAngular();
		};
		
		devolucao.classificarModelo = function(tipo){
			utils.select('div#s2id_modelo', tipo);
			browser.waitForAngular();
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
			browser.sleep(16000);
			element(by.id('btn_exec_finalizar-texto-devolucao')).click();
		};
	};

	module.exports = DevolucaoPage;
	
})();