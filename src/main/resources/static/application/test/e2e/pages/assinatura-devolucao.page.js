/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 07.12.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var AssinaturaDevolucaoPage = function () {
		var assinaturaDevolucao = this;
		
		this.assinar = function() {
			element(by.id('botaoFinalizarAssinaturaDevolucao')).click();
			
			browser.waitForAngular();
		};
		
		this.mensagemSucesso = function() {
			return element(by.xpath("//*[contains(text(), '1 documento(s) de devolução assinados com sucesso.')]"));
		};
	};

	module.exports = AssinaturaDevolucaoPage;
	
})();