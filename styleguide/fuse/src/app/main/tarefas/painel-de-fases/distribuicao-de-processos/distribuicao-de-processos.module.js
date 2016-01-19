(function() {
	'use strict';

	angular
		.module('app.tarefas.painel-de-fases.distribuicao-de-processos', ['app.tarefas.painel-de-fases', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas/painel-de-fases/distribuicao-de-processos');
		
		$stateProvider
			.state('app.tarefas.painel-de-fases.distribuicao-de-processos', {
				url: '/distribuicao-de-processos',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/painel-de-fases/distribuicao-de-processos/distribuicao-de-processos.html'
					}
				}
			});

        msNavigationServiceProvider.saveItem('tarefas.painel-de-fases.distribuicao-de-processos', {
            title      : 'Distribuição de Processos',
            icon       : 'icon-trello',
            state      : 'app.tarefas.painel-de-fases.distribuicao-de-processos',
            translation: 'TAREFAS.PAINEL-DE-FASES.DISTRIBUICAO-DE-PROCESSOS',
            weight     : 1
        });
	}
})();