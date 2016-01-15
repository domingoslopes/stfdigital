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
						templateUrl: 'app/main/gestao/meus-paineis/meus-paineis.html',
						controller: 'GestaoMeusPaineisController',
						controllerAs: 'vm'
					},
                    'principal@app.gestao.meus-paineis': {
                        templateUrl: 'app/main/gestao/meus-paineis/principal/principal.html',
                        controller: 'GestaoMeusPaineisPrincipalController',
                        controllerAs: 'vm'
                    },
                    'peticoes@app.gestao.meus-paineis': {
                        templateUrl: 'app/main/gestao/meus-paineis/peticoes/peticoes.html',
                        controller: 'GestaoMeusPaineisPeticoesController',
                        controllerAs: 'vm'
                    },
                    'autuacoes@app.gestao.meus-paineis': {
                        templateUrl: 'app/main/gestao/meus-paineis/autuacoes/autuacoes.html',
                        //controller: 'GestaoMeusPaineisAutuacoesController',
                        //controllerAs: 'vm'
                    },
                    'produtividade-do-time@app.gestao.meus-paineis': {
                        templateUrl: 'app/main/gestao/meus-paineis/produtividade-do-time/produtividade-do-time.html',
                        //controller: 'GestaoMeusPaineisProdutividadeDoTimeController',
                        //controllerAs: 'vm'
                    }
				}
			})
            .state('app.gestao.meus-paineis.principal', {
                url: '/principal',
            })
            .state('app.gestao.meus-paineis.peticoes', {
                url: '/peticoes',
            })
            .state('app.gestao.meus-paineis.autuacoes', {
                url: '/autuacoes',
            })
            .state('app.gestao.meus-paineis.produtividade-do-time', {
                url: '/produtividade-do-time',
            });

        msNavigationServiceProvider.saveItem('gestao.meus-paineis', {
            title      : 'Meus Paineis',
            icon       : 'icon-view-dashboard',
            state      : 'app.gestao.meus-paineis',
            translation: 'GESTAO.MEUS-PAINEIS.MEUS-PAINEIS',
            weight     : 1
        });
	}
})();
