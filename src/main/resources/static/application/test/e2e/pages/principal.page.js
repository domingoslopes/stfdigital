/**
 * Representa a Dashboard da aplicação. Implementa o pattern descrito em
 * <code>https://github.com/angular/protractor/blob/master/docs/page-objects.md</code>
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 15.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var PrincipalPage = function () {
		browser.get('/');
		this.conteudo = element(by.css('body'));
		
		this.iniciarProcesso = function () {
			browser.actions().mouseMove(element(by.id('novoItemIcon'))).perform();
			// É necessário mover o mouse para cima do link, caso contrário o click não vai funcionar
			browser.actions().mouseMove(element(by.css('a[ui-sref="registro"]'))).perform();
			element(by.css('a[ui-sref="registro"]')).click();
			
			// Força a saída do mouse da barra de menus para que essa barra recue à esquerda
			browser.actions().mouseMove(element(by.id('papeis'))).perform();
		};

		this.executarTarefa = function() {
			element(by.repeater('tarefa in tarefas').row(0)).element(by.css('a')).click();
		};
		
		this.tarefas = function () {
			return element.all(by.repeater('tarefa in tarefas'));
		};
		
		this.login = function (papel) {
		    element(by.id('papeisButton')).click();
		    
		    element(by.id(papel)).click();
		    
		    browser.waitForAngular();
		};
		
	};

	module.exports = PrincipalPage;
	
})();
