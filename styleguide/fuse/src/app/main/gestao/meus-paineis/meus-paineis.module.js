(function() {
	'use strict';

	angular
		.module('app.gestao.meus-paineis', ['app.gestao', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/gestao/meus-paineis');
		
		$stateProvider
			.state('app.gestao.meus-paineis', {
				url: '/meus-paineis',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/gestao/meus-paineis/meus-paineis.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('gestao.meus-paineis', {
            title      : 'Meus Paineis',
            icon       : 'icon-view-dashboard',
            state      : 'app.gestao.meus-paineis',
            translation: 'MEUS-PAINEIS.MEUS-PAINEIS',
            weight     : 1
        });
	}
})();