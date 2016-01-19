(function() {
	'use strict';

	angular
		.module('app.tarefas.painel-de-fases.recebimento-de-remessas', ['app.tarefas.painel-de-fases', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas/painel-de-fases/recebimento-de-remessas');
		
		$stateProvider
			.state('app.tarefas.painel-de-fases.recebimento-de-remessas', {
				url: '/recebimento-de-remessas',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/painel-de-fases/recebimento-de-remessas/recebimento-de-remessas.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('tarefas.painel-de-fases.recebimento-de-remessas', {
            title      : 'Recebimento de Remessas',
            icon       : 'icon-trello',
            state      : 'app.tarefas.painel-de-fases.recebimento-de-remessas',
            translation: 'TAREFAS.PAINEL-DE-FASES.RECEBIMENTO-DE-REMESSAS',
            weight     : 1
        });
	}
})();