(function() {
	'use strict';

	angular.module('app.gestao', ['app.autenticado', 'classy']).config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/gestao');
		
		$stateProvider
			.state('app.gestao', {
				abstract: true,
				url: '/gestao',
				parent: 'app.autenticado',
			});

		// Navigation
        msNavigationServiceProvider.saveItem('gestao', {
            title : 'GESTÃO',
            translation: 'GESTAO.GESTAO',
            group : true,
            weight: 1
        });
	}
})();