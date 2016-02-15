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
		
		this.dashletGestaoAutuacao = element(by.cssContainingText('.panel-title', 'Gráfico de Autuações'));

		this.iniciarProcesso = function (idIcon) {
			browser.waitForAngular();
//			browser.actions().mouseMove(element(by.css('i.pg-home'))).perform();
			var elem = element(by.id(idIcon));
//			browser.actions().mouseMove(elem).perform();
//			browser.wait(elem.isDisplayed, 3000); // Aguarda o menu aparecer com timeout de 3000
//			elem.click();
			
			browser.executeScript("arguments[0].click()", elem.getWebElement());
			
			browser.waitForAngular();
			
			// Força a saída do mouse da barra de menus para que essa barra recue à esquerda
//			browser.actions().mouseMove(element(by.id('papeis'))).perform();
			
			// Aguarda o menu desaparecer com timeout de 3000
//			browser.wait(function() {
//				return elem.isDisplayed().then(function(isDisplayed) {
//					return !isDisplayed;
//				});
//			}, 3000);
		};
		
		this.executarTarefa = function() {

			var tarefa = element(by.repeater('tarefa in tarefas').row(0));
			tarefa.element(by.css('input')).click();
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
		
		this.tarefas = function () {
			browser.waitForAngular();
			return element.all(by.repeater('tarefa in tarefas'));
		};
		
		this.peticoes = function () {
			browser.waitForAngular();
			return element.all(by.repeater('peticao in peticoes'));
		};
		
		this.isMensagemSucessoApresentada = function() {
			return element(by.css('div.pgn-wrapper')).all(by.css('div.alert.alert-success')).get(0).isDisplayed();
		};
		
		this.dashletMinhasTarefas = element(by.cssContainingText('.panel-title', 'Tarefas dos meus papéis'));
		
		this.dashletMinhasPeticoes = element(by.cssContainingText('.panel-title', 'Minhas Petições'));
	};

	module.exports = PrincipalPage;
	
})();
