/**
 * @author Lucas Mariano
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 06.07.2015
 */ 
(function() {
	'use strict';

	angular.element(document).ready(function() {
		angular.bootstrap(document, ['app']);
	});

	angular.module('app', ['ui.router', 'plataforma', 'autuacao', 'templates'])//, 'mocks'])
	
	.config(function($stateProvider, $urlRouterProvider, $logProvider, $httpProvider) {
		$httpProvider.interceptors.push('error-handler');
		$urlRouterProvider.otherwise('/');
		$logProvider.debugEnabled(true);
		$stateProvider.state('root', {
		});
	})
	
	.value('version', '0.1.0');
	
})();
