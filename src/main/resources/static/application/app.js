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

	angular.module('app', ['ui.router', 'plataforma', 'autuacao', 'templates', 'ngMockE2E'])
	
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
		$httpBackend.whenGET('/papeis').respond({papeis : [{nome : 'Peticionador'}, {nome : 'Autuador'}, {nome : 'Distribuidor'} ]});
		$httpBackend.whenGET('/api/tarefas').respond([
	        {id: '01', nome : 'preautuacao', descricao : 'Pré-autuar Processo'}, 
	        {id: '02', nome : 'autuacao', descricao : 'Autuar Processo'}, 
	        {id: '03', nome : 'distribuicao', descricao : 'Distribuir Processo'}
	    ]);
	})
	
	.controller('MainController', function ($log) {
		$log.debug('Main Controller carregado!');
	})
	
	.value('version', '0.1.0');
	
})();
