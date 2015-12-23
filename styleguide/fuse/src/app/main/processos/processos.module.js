(function() {
	'use strict';

	angular
		.module('app.processos', ['app.autenticado', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
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
})();