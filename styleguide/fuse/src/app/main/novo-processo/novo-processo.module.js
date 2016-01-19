(function() {
	'use strict';

	angular
		.module('app.novo-processo', ['app.autenticado', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/novo-processo');
		
		$stateProvider
			.state('app.novo-processo', {
				parent: 'app.autenticado',
				url: '/novo-processo',
                views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/novo-processo/novo-processo.html',
						controller: 'NovoProcessoController',
						controllerAs: 'vm'
					},
				}
			});
	}
})();