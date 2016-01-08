(function() {
	'use strict';

	angular
		.module('app.tarefas.painel-de-fases', ['app.tarefas', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas/painel-de-fases');
		
		$stateProvider
			.state('app.tarefas.painel-de-fases', {
				url: '/painel-de-fases',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/painel-de-fases/painel-de-fases.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('tarefas.painel-de-fases', {
            title      : 'Painel de Fases',
            icon       : 'icon-view-week',
            state      : 'app.tarefas.painel-de-fases',
            translation: 'TAREFAS.PAINEL-DE-FASES.PAINEL-DE-FASES',
            weight     : 1
        });
	}
})();