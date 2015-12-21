(function() {
	'use strict';

	angular
		.module('app.nao-autenticado.cadastro', ['classy', 'ngMask'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/nao-autenticado/cadastro');

		$stateProvider
			.state('app.nao-autenticado.cadastro', {
				url: '/cadastro',
				views: {
					'form@app.nao-autenticado': {
						templateUrl: 'app/main/nao-autenticado/cadastro/cadastro.html',
						controller: 'CadastroController',
						controllerAs: 'vm'
					}
				}
			});
	}
})();