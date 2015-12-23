(function() {
	'use strict';

	angular
		.module('app.nao-autenticado', ['classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, $translateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/nao-autenticado');
		
		$stateProvider
			.state('app.nao-autenticado', {
				abstract: true,
				views: {
					'main@': {
	                    templateUrl: 'app/core/layouts/content-only.html',
	                    controller : 'MainController',
	                    controllerAs: 'vm'
	                },
					'content@app.nao-autenticado': {
						templateUrl: 'app/main/nao-autenticado/nao-autenticado.html'
					}
				},
				bodyClass: 'login-v2'
			});
	}
})();