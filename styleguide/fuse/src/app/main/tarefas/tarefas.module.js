(function() {
	'use strict';

	angular
		.module('app.tarefas', ['app.autenticado', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas');
		
		$stateProvider
			.state('app.tarefas', {
				abstract: true,
				url: '/tarefas',
				parent: 'app.autenticado',
			});

		// Navigation
        msNavigationServiceProvider.saveItem('tarefas', {
            title : 'TAREFAS',
            translation: 'TAREFAS.TAREFAS',
            group : true,
            weight: 1
        });
	}
})();