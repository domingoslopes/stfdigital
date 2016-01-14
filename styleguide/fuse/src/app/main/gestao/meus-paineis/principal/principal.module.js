(function() {
	'use strict';

	angular
		.module('app.gestao.meus-paineis.principal', ['app.gestao.meus-paineis', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/gestao/meus-paineis/principal');
		
		$stateProvider
			.state('app.gestao.meus-paineis.principal', {
				url: '/principal',
				views: {
					'principal@app.gestao.meus-paineis': {
						templateUrl: 'app/main/gestao/meus-paineis/principal/principal.html',
						controller: 'GestaoMeusPaineisPrincipalController',
						controllerAs: 'vm'
					}
				}
			});

        msNavigationServiceProvider.saveItem('gestao.meus-paineis.principal', {
            title      : 'Principal',
            state      : 'app.gestao.meus-paineis.principal',
            translation: 'GESTAO.MEUS-PAINEIS.PRINCIPAL',
            weight     : 1,
            hidden     : true
        });
	}
})();