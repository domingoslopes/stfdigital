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
		angular.bootstrap(document, ['appDev']);
	});

	angular.module('appDev', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous', 'plataforma', 'autuacao', 'templates', 'properties', 'ui.select2', 'ngSanitize', 'mocks'])
	
	.config(function($stateProvider, $urlRouterProvider, $logProvider, $httpProvider, $locationProvider) {
		$httpProvider.interceptors.push('error-handler');
		$urlRouterProvider.otherwise('/dashboard');
		$locationProvider.html5Mode(true);
		$logProvider.debugEnabled(true);
		$stateProvider.state('root', {
		});
	})
	.value('version', '0.1.0');
	
})();
