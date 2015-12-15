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

		this.conteudo = element(by.css('body'));
		
		this.titleGestaoAutuacao = element(by.id('gestaoAutuacaoId'));

		this.iniciarProcesso = function (idIcon) {
			browser.waitForAngular();
			browser.actions().mouseMove(element(by.css('i.pg-home'))).perform();
			var elem = element(by.id(idIcon));
			browser.actions().mouseMove(elem).perform();
			elem.click();
			
			// Força a saída do mouse da barra de menus para que essa barra recue à esquerda
			browser.actions().mouseMove(element(by.id('papeis'))).perform();
		};
		
		this.executarTarefa = function() {
			element(by.repeater('tarefa in tarefas').row(0)).element(by.css('a')).click();
			browser.waitForAngular();
		};
		
		this.tarefas = function () {
			return element.all(by.repeater('tarefa in tarefas'));
		};
		
		this.peticoes = function () {
			return element.all(by.repeater('peticao in peticoes'));
		};
		
		this.dashletMinhasTarefas = element.all(by.css('.dashlet'))
				.all(by.cssContainingText('.panel-title', 'Minhas Tarefas'));
		
		this.dashletMinhasPeticoes = element.all(by.css('.dashlet'))
			.all(by.cssContainingText('.panel-title', 'Minhas Petições'));
	};

	module.exports = PrincipalPage;
	
})();
