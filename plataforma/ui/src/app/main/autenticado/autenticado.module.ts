module app.autenticado {
	'use strict';
	import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
	import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
					$stateProvider: IStateProvider) {

		$translatePartialLoaderProvider.addPart('app/main/autenticado');

		$stateProvider.state('app.autenticado', {
			abstract: true,
			views: {
				'main@': {
					templateUrl: 'app/core/layouts/vertical-navigation.html',
					controller: 'app.MainController',
					controllerAs: 'vm'
				},
				'toolbar@app.autenticado': {
					templateUrl: 'app/toolbar/toolbar.html',
					controller: 'ToolbarController',
					controllerAs: 'vm'
				},
				'navigation@app.autenticado': {
					templateUrl: 'app/navigation/navigation.html',
					controller: 'NavigationController',
					controllerAs: 'vm'
				},
			}
		});
	}

	angular
		.module('app.autenticado', [])
		.config(config);
}