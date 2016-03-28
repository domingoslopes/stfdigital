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
			browser.isElementPresent(by.id('painel-tags')).then(function() {
				browser.waitForAngular();
			});
		};
		
		devolucao.gerarTexto = function() {
			element(by.id('botao-gerar-texto')).click();
			browser.isElementPresent(by.id('painel-editor')).then(function() {
				browser.waitForAngular();
			});
		};
		
		devolucao.finalizar = function() {
			var btn = by.id('btn_exec_finalizar-texto-devolucao')
			browser.isElementPresent(btn).then(function() {
				element(btn).click();
			});
		};
	};

	module.exports = DevolucaoPage;
	
})();