module app.tarefas.notificacoes {
	'use strict';

	import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
	import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
					$stateProvider: IStateProvider,
					msNavigationServiceProvider: any) {
		
		$translatePartialLoaderProvider.addPart('app/main/tarefas/notificacoes');

		$stateProvider
			.state('app.tarefas.notificacoes', {
				url: '/notificacoes',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/notificacoes/notificacoes.html'
					}
				}
			});

		msNavigationServiceProvider.saveItem('tarefas.notificacoes', {
			title      : 'Notificações',
			icon       : 'icon-comment-alert',
			state      : 'app.tarefas.notificacoes',
			translation: 'TAREFAS.NOTIFICACOES.NOTIFICACOES',
			weight     : 1,
			badge : {
				content: 2,
				color  : '#D22E2E'
			},
		});
	}
	angular
		.module('app.tarefas.notificacoes', ['app.tarefas'])
		.config(config);
}