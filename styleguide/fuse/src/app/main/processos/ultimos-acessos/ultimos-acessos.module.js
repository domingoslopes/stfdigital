(function() {
	'use strict';

	angular
		.module('app.processos.ultimos-acessos', ['app.processos', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/processos/ultimos-acessos');
		
		$stateProvider
			.state('app.processos.ultimos-acessos', {
				url: '/ultimos-acessos',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/processos/ultimos-acessos/ultimos-acessos.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('processos.ultimos-acessos', {
            title      : 'Últimos Acessos',
            icon       : 'icon-history',
            state      : 'app.processos.ultimos-acessos',
            translation: 'ULTIMOS-ACESSOS.ULTIMOS-ACESSOS',
            weight     : 1
        });
	}
})();