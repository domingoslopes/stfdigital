(function() {
	'use strict';

	angular
		.module('app.nao-autenticado.login', ['classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/nao-autenticado/login');

		$stateProvider
			.state('app.nao-autenticado.login', {
				url: '/login',
				views: {
					'form@app.nao-autenticado': {
						templateUrl: 'app/main/nao-autenticado/login/login.html',
						controller: 'LoginController',
						controllerAs: 'vm'
					}
				}
			});
	}
})();