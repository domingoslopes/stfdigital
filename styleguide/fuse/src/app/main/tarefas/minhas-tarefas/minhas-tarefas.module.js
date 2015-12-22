(function() {
	'use strict';

	angular
		.module('app.tarefas.minhas-tarefas', ['app.tarefas', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas/minhas-tarefas');
		
		$stateProvider
			.state('app.tarefas.minhas-tarefas', {
				url: '/minhas-tarefas',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/minhas-tarefas/minhas-tarefas.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('tarefas.minhas-tarefas', {
            title      : 'Minhas Tarefas',
            icon       : 'icon-view-list',
            state      : 'app.tarefas.minhas-tarefas',
            translation: 'MEUS-PAINEIS.MEUS-PAINEIS',
            weight     : 1,
            badge : {
                content: 1,
                color  : '#03A9F3'
            },
        });
	}
})();