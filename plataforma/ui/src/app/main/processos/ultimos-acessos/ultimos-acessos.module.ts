module app.processos.ultimosAcessos {
	'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider) {

		$translatePartialLoaderProvider.addPart('app/main/processos/ultimos-acessos');

		$stateProvider
			.state('app.processos.ultimos-acessos', {
                url: '/ultimos-acessos',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/processos/ultimos-acessos/ultimos-acessos.html',
                        controller: 'app.processos.ultimos-acessos.UltimosAcessosController',
                        controllerAs: 'vm'
					}
				},
                resolve: {
                    ultimosAcessos : ['app.processos.ultimos-acessos.UltimosAcessosService', (ultimosAcessosService: UltimosAcessosService) => {
                        return ultimosAcessosService.acessos();
                    }]
                }
			})
            .state('app.processos.ultimos-acessos.todos', {
                url: '/todos'
            })
            .state('app.processos.ultimos-acessos.peticoes', {
                url: '/peticoes'
            })
            .state('app.processos.ultimos-acessos.processos', {
                url: '/processos'
            })
            .state('app.processos.ultimos-acessos.pecas', {
                url: '/pecas'
            });

        msNavigationServiceProvider.saveItem('processos.ultimos-acessos', {
            title      : 'Últimos Acessos',
            icon       : 'icon-history',
            state      : 'app.processos.ultimos-acessos',
            translation: 'PROCESSOS.ULTIMOS-ACESSOS.ULTIMOS-ACESSOS',
            weight     : 1
        });
	}

    angular
        .module('app.processos.ultimos-acessos', ['app.processos'])
        .config(config);
}
