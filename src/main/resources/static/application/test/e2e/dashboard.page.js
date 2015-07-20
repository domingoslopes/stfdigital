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

	var DashboardPage = function () {
		browser.get('/');
		this.conteudo = element(by.css('body'));
		
		this.iniciarProcesso = function () {
			browser.actions().mouseMove(element(by.id('novoItemIcon'))).perform();
			// É necessário mover o mouse para cima do link, caso contrário o click não vai funcionar
			browser.actions().mouseMove(element(by.css('a[ui-sref="registro"]'))).perform();
			element(by.css('a[ui-sref="registro"]')).click();
		}
	};

	module.exports = DashboardPage;
	
})();
