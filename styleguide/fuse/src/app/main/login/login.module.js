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
					'content@app': {
						templateUrl: 'app/main/login/login.html',
						controller: 'LoginController'
					}
				}
			});
	}
})();