(function() {
	'use strict';

	angular
		.module('app.configuracoes.meu-perfil', ['app.configuracoes', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/configuracoes/meu-perfil');
		
		$stateProvider
			.state('app.configuracoes.meu-perfil', {
				url: '/meu-perfil',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/configuracoes/meu-perfil/meu-perfil.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('configuracoes.meu-perfil', {
            title      : 'Meu Perfil',
            icon       : 'icon-account',
            state      : 'app.configuracoes.meu-perfil',
            translation: 'MEUS-PAINEIS.MEUS-PAINEIS',
            weight     : 1
        });
	}
})();