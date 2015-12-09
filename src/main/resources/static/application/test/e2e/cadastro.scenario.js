/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 03.12.2015
 */

/*jshint undef:false */
(function() {
	'use strict';
	
	describe('Realizar cadastro', function() {
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria acessar a página de login', function() {
			browser.get('/login');
		});
		
		it('Deveria acessar a página de cadastro através do botão de cadastro', function() {
			var e = element(by.id('cadastro'));
			
			expect(e.isPresent()).toBeTruthy();
			
			element(by.id('cadastro')).click();
			
			browser.ignoreSynchronization = true;
			return browser.driver.wait(function() {
				return browser.driver.getCurrentUrl().then(function(url) {
					browser.ignoreSynchronization = false;
					return /cadastro/.test(url);
				});
			}, 10000);
			browser.ignoreSynchronization = false;
		});
	});
})();