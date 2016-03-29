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
			var elem = element(by.id(idIcon));
			browser.executeScript("arguments[0].click()", elem.getWebElement());
			browser.waitForAngular();
		};
		
		this.executarTarefa = function() {

			var tarefa = element(by.repeater('tarefa in tarefasDosPapeis').row(0));
			tarefa.element(by.css('input')).click();
			browser.waitForAngular();
			
			var acao = element(by.css('actions'));
			acao.element(by.css('a.dropdown-toggle')).click();
			acao.element(by.repeater('action in actions').row(0)).click();
			browser.waitForAngular();
			
			var assumirTarefa = by.id('btn_exec_assumir-tarefa');
			browser.wait(browser.isElementPresent(assumirTarefa));
			element(assumirTarefa).click();
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
