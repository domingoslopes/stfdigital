/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var utils = require('../utils');
	
	describe('app', function() {
		beforeEach(function() {
			console.info('\nrunning:', jasmine.getEnv().currentSpec.description);
		});

		afterEach(function() {
			if (!jasmine.getEnv().currentSpec.results().passed()) {
				utils.takeScreenshot(browser, jasmine.getEnv().currentSpec.description.replace(/ /g, '-'));
			}
		});

		it('Deveria carregar o dashboard do usuário', function() {
			browser.get('/');
			expect(browser.isElementPresent(by.css('body'))).toBe(true);
		});

		it('Deveria navegar para a página de registro de petições físicas', function() {
			browser.actions().mouseMove(element(by.id('foo'))).perform();
			// É necessário mover o mouse para cima do link, caso contrário o click não vai funcionar
			browser.actions().mouseMove(element(by.css('a[ui-sref="root.peticao-fisica"]'))).perform();
			element(by.css('a[ui-sref="root.peticao-fisica"]')).click();
			expect(browser.getCurrentUrl()).toMatch(/\/peticao-fisica/);
		});

	});
})();
