module app.login {
	'use strict';

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ng.translate.ITranslatePartialLoaderProvider,
					$stateProvider: ng.ui.IStateProvider) {

		$translatePartialLoaderProvider.addPart('app/main/login');

		$stateProvider
			.state('app.login', {
				parent: 'app.nao-autenticado',
				url: '/login',
				views: {
					'form@app.nao-autenticado': {
						templateUrl: 'app/main/login/login.html',
						controller: LoginController,
						controllerAs: 'vm'
					}
				}
			});
	}

	angular
		.module('app.login', ['app.nao-autenticado'])
		.config(config);
}