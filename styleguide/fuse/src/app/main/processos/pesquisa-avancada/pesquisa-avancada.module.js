(function() {
	'use strict';

	angular
		.module('app.processos.pesquisa-avancada', ['app.processos', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/processos/pesquisa-avancada');
		
		$stateProvider
			.state('app.processos.pesquisa-avancada', {
				url: '/pesquisa-avancada',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/processos/pesquisa-avancada/pesquisa-avancada.html',
						controller: 'ProcessosPesquisaAvancadaController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					traits: /** @ngInject */ function($http) {
						var traits = {data:{}};

						$http.get('app/data/sample/processos/pesquisa-avancada/traits.json').then(function(response) {
							traits.data = response.data;
						});

						return traits;
					}
				}
			});

        msNavigationServiceProvider.saveItem('processos.pesquisa-avancada', {
            title      : 'Pesquisa Avançada',
            icon       : 'icon-magnify',
            state      : 'app.processos.pesquisa-avancada',
            translation: 'PROCESSOS.PESQUISA-AVANCADA.PESQUISA-AVANCADA',
            weight     : 1
        });
	}
})();