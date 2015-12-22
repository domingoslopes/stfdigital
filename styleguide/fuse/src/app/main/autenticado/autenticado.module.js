(function() {
	'use strict';

	angular
		.module('app.autenticado', ['classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, $translateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/autenticado');
		
		$stateProvider
			.state('app.autenticado', {
				//abstract: true,
				url: '/base',
				views: {
					'main@': {
	                    templateUrl: 'app/core/layouts/vertical-navigation.html',
	                    controller : 'MainController',
	                    controllerAs: 'vm'
	                },
					'content@app.autenticado': {
						templateUrl: 'app/main/autenticado/autenticado.html'
					},
					'toolbar@app.autenticado'   : {
                        templateUrl: 'app/toolbar/layouts/vertical-navigation/toolbar.html',
                        controller : 'ToolbarController',
                        controllerAs: 'vm'
                    },
                    'navigation@app.autenticado': {
                        templateUrl: 'app/navigation/layouts/vertical-navigation/navigation.html',
                        controller : 'NavigationController',
                        controllerAs: 'vm'
                    },
				}
			});
	}
})();