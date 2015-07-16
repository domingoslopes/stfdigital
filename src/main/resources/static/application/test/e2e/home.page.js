/**
 * Representa a Home Page da aplicação. Implementa o pattern descrito em
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

	var HomePage = function () {
		browser.get('/');
		this.conteudo = element(by.css('body'));
		
		this.iniciarProcesso = function () {
			browser.actions().mouseMove(element(by.id('novoItemIcon'))).perform();
			// É necessário mover o mouse para cima do link, caso contrário o click não vai funcionar
			browser.actions().mouseMove(element(by.css('a[ui-sref="root.peticao-fisica"]'))).perform();
			element(by.css('a[ui-sref="root.peticao-fisica"]')).click();
		}
	};

	module.exports = HomePage;
	
})();
