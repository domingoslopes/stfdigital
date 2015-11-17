/**
 * Implementa o pattern descrito em
 * <code>https://github.com/angular/protractor/blob/master/docs/page-objects.md</code>
 * 
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 */
/*jshint undef:false */
(function() {
	'use strict';

	var LoginPage = function () {

		browser.get('/login'); 
		
		this.login = function (papel) {
			
		    element(by.id('username')).sendKeys(papel);
		    element(by.id('password')).sendKeys('123');
		    
		    element(by.id('entrar')).click();
		    
		    // O login pode levar algum tempo, já que recarrega toda a página.
		    // Para garantir que o login ocorreu completamente, nós esperamos ele redirecionar
		    // para o dashboard e só retornamos quando ele validar a URL.
			return browser.driver.wait(function() {
				return browser.driver.getCurrentUrl().then(function(url) {
					browser.ignoreSynchronization = false;
					return /dashboard/.test(url);
				});
			}, 10000);
		};
		
		this.logout = function() {
			element(by.id('papeisButton')).click();
			element(by.id('logout')).click();
				
			return browser.driver.wait(function() {
				return browser.driver.getCurrentUrl().then(function(url) {
					return /login/.test(url);
				});
			}, 10000);
		};		
	};

	module.exports = LoginPage;
	
})();
