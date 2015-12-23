(function() {
	'use strict';

	angular
		.module('app.cadastro', ['app.nao-autenticado', 'classy', 'ngMask'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/cadastro');

		$stateProvider
			.state('app.cadastro', {
				parent: 'app.nao-autenticado',
				url: '/cadastro',
				views: {
					'form@app.nao-autenticado': {
						templateUrl: 'app/main/cadastro/cadastro.html',
						controller: 'CadastroController',
						controllerAs: 'vm'
					}
				}
			});
	}
})();