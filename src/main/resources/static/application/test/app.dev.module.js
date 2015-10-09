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

	angular.module('appDev', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous', 'plataforma', 'autuacao', 'templates', 'properties', 'ui.select2', 'ngSanitize', 'angularFileUpload', 'mocks'])
	
	.config(function($stateProvider, $urlRouterProvider, $logProvider, $httpProvider, $locationProvider) {
		$httpProvider.interceptors.push('error-handler');
		$urlRouterProvider.otherwise('/dashboard');
		$locationProvider.html5Mode({
			enabled: true,
			requireBase: false, // Alterando o requireBase para false, pois estava causando erro no teste unitário
		});
		$logProvider.debugEnabled(false);
		$stateProvider.state('root', {
		});
	})
	.run(function(ActionService, $state) {
		ActionService.load("autuacao");
	})
	.value('version', '0.1.0');
	
})();
