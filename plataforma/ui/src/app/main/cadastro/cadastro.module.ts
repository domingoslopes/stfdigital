module app.cadastro {
	'use strict';
	import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
	import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
	function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
					$stateProvider: IStateProvider) {

		$translatePartialLoaderProvider.addPart('app/main/cadastro');

		$stateProvider.state('app.cadastro', {
			parent: 'app.nao-autenticado',
			url: '/cadastro',
			views: {
				'form@app.nao-autenticado': {
					templateUrl: 'app/main/cadastro/cadastro.html',
					controller: CadastroController,
					controllerAs: 'vm'
				}
			}
		});
	}

	angular
		.module('app.cadastro', ['app.nao-autenticado', 'ngMask'])
		.config(config);
}