module app.processos {
	'use strict';
	import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
	import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
					$stateProvider: IStateProvider,
					msNavigationServiceProvider) {

		$translatePartialLoaderProvider.addPart('app/main/processos');
		
		$stateProvider
			.state('app.processos', {
				abstract: true,
				url: '/processos',
				parent: 'app.autenticado',
			});

		// Navigation
        msNavigationServiceProvider.saveItem('processos', {
            title : 'PROCESSOS',
            translation: 'PROCESSOS.PROCESSOS',
            group : true,
            weight: 1
        });
	}

	angular
		.module('app.processos', ['app.autenticado'])
		.config(config);
}