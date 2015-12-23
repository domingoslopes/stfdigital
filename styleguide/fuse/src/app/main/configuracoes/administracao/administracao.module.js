(function() {
	'use strict';

	angular
		.module('app.configuracoes.administracao', ['app.configuracoes', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/configuracoes/administracao');
		
		$stateProvider
			.state('app.configuracoes.administracao', {
				url: '/administracao',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/configuracoes/administracao/administracao.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('configuracoes.administracao', {
            title      : 'Administração',
            icon       : 'icon-cog',
            state      : 'app.configuracoes.administracao',
            translation: 'MEUS-PAINEIS.MEUS-PAINEIS',
            weight     : 1
        });
	}
})();