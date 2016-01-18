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
                redirectTo: 'app.processos.ultimos-acessos.todos',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/processos/ultimos-acessos/ultimos-acessos.html',
                        controller: 'ProcessosUltimosAcessosController',
                        controllerAs: 'vm'
					}
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
})();
