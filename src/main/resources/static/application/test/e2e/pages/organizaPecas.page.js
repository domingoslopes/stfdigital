/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 12.02.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var Utils = require('./support');
	
	var utils = new Utils();
	
	var OrganizaPecasPage = function () {
		
		this.arrastarPecas = function(){
			browser.actions().dragAndDrop(element(by.css("#tabelaPecas tr:nth-child(1)")), element(by.css("#tabelaPecas tr:nth-child(3)"))).mouseDrown().perform();
		};
		
		this.executarAcao = function(acao) {

			var peca = element(by.repeater('peca in organiza.processo.pecas').row(0));
			peca.element(by.css('input')).click();
			browser.waitForAngular();
			
			var acao = element(by.css('actions'));
			acao.element(by.css('a.dropdown-toggle')).click();
			acao.element(by.repeater('action in actions').row(0)).click();
			browser.waitForAngular();
			
			browser.wait(element(by.id('btn_exec_assumir-tarefa')).isDisplayed, 3000);
			element(by.id('btn_exec_assumir-tarefa')).click();
			tarefa.element(by.css('a')).click();
			browser.waitForAngular();
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_organizar-pecas')).click();
		};
		
	};

	module.exports = OrganizaPecasPage;
	
})();
