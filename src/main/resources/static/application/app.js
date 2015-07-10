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

	angular.module('app', ['ui.router', 'plataforma', 'autuacao', 'version', 'error-handler', 'templates', 'ngMockE2E'])
	
	.config(function($stateProvider, $urlRouterProvider, $logProvider, $httpProvider) {
		$urlRouterProvider.otherwise('/');
		$logProvider.debugEnabled(true);
		$httpProvider.interceptors.push('error-handler');
		$stateProvider.state('root', {
			views : {}
		});
	})
	
	.run(function($log, $httpBackend) {
		$log.debug('Aplicação carregada. Mocando as services...');
		$httpBackend.whenGET(/\/.*.tpl.html/).passThrough();
		$httpBackend.whenGET('/papeis').respond({papeis : [ {nome : 'Peticionador'}, {nome : 'Autuador'}, {nome : 'Distribuidor'} ]});
		$httpBackend.whenGET('/api/tarefas').respond([{descricao : 'Pré-autuar petição #00001'}, {descricao : 'Pré-autuar petição #00002'}, {descricao : 'Pré-autuar petição #00003'}]);
	})
	
	.controller('MainController', function ($log) {
		$log.debug('Main Controller carregado!');
	})
	
	.value('version', '0.1.0');
	
})();
