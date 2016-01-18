(function() {
	'use strict';

	angular
		.module('app.tarefas.painel-de-fases.processo-de-autuacao', ['app.tarefas.painel-de-fases', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas/painel-de-fases/processo-de-autuacao');
		
		$stateProvider
			.state('app.tarefas.painel-de-fases.processo-de-autuacao', {
				url: '/processo-de-autuacao',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/painel-de-fases/processo-de-autuacao/processo-de-autuacao.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('tarefas.painel-de-fases.processo-de-autuacao', {
            title      : 'Processo de Autuação',
            icon       : 'icon-trello',
            state      : 'app.tarefas.painel-de-fases.processo-de-autuacao',
            translation: 'TAREFAS.PAINEL-DE-FASES.PROCESSO-DE-AUTUACAO',
            weight     : 1
        });
	}
})();