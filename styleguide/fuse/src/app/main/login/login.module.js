(function() {
	'use strict';

	angular
		.module('app.login', ['app.nao-autenticado', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/login');

		$stateProvider
			.state('app.login', {
				parent: 'app.nao-autenticado',
				url: '/login',
				views: {
					'form@app.nao-autenticado': {
						templateUrl: 'app/main/login/login.html',
						controller: 'LoginController',
						controllerAs: 'vm'
					}
				}
			});
	}
})();