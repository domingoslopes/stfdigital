(function() {
	'use strict';

	angular
		.module('app.gestao.meus-paineis.peticoes', ['app.gestao.meus-paineis', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/gestao/meus-paineis/peticoes');
		
		$stateProvider
			.state('app.gestao.meus-paineis.peticoes', {
				url: '/peticoes',
				views: {
					'peticoes@app.gestao.meus-paineis': {
						templateUrl: 'app/main/gestao/meus-paineis/peticoes/peticoes.html',
						controller: 'GestaoMeusPaineisPrincipalController',
						controllerAs: 'vm'
					}
				}
			});

        msNavigationServiceProvider.saveItem('gestao.meus-paineis.peticoes', {
            title      : 'Petições',
            state      : 'app.gestao.meus-paineis.peticoes',
            translation: 'GESTAO.MEUS-PAINEIS.PETICOES',
            weight     : 1,
            hidden     : true
        });
	}
})();