module app.configuracoes.administracao {
	'use strict';
	import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
	import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
					$stateProvider: IStateProvider,
					msNavigationServiceProvider: any) {

		$translatePartialLoaderProvider.addPart('app/main/configuracoes/administracao');

		$stateProvider.state('app.configuracoes.administracao', {
			url: '/administracao',
			views: {
				'content@app.autenticado': {
					templateUrl: 'app/main/configuracoes/administracao/administracao.html'
				}
			}
		});

		msNavigationServiceProvider.saveItem('configuracoes.administracao', {
			title      : 'Administração',
			icon       : 'icon-cog',
			state      : 'app.configuracoes.administracao',
			translation: 'CONFIGURACOES.ADMINISTRACAO.ADMINISTRACAO',
			weight     : 1
		});
	}

	angular
		.module('app.configuracoes.administracao', ['app.configuracoes'])
		.config(config);
}