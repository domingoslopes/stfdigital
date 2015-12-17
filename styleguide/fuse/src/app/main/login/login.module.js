(function() {
	'use strict';

	angular
		.module('app.login', ['classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider) {
		$translatePartialLoaderProvider.addPart('app/main/login');

		$stateProvider
			.state('app.login', {
				url: '/login',
				views: {
					'main@': {
	                    templateUrl: 'app/core/layouts/content-only.html',
	                    controller : 'MainController as vm'
	                },
					'content@app.login': {
						templateUrl: 'app/main/login/login.html',
						controller: 'LoginController'
					}
				},
				bodyClass: 'login-v2'
			});
	}
})();