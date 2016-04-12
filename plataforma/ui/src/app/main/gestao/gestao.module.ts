module app.gestao {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

	/** @ngInject **/
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                     $stateProvider: IStateProvider,
                     msNavigationServiceProvider: any) {

        $translatePartialLoaderProvider.addPart('app/main/gestao');

        $stateProvider
            .state('app.gestao', {
                abstract: true,
                url: '/gestao',
                parent: 'app.autenticado',
            });

        // Navigation
        msNavigationServiceProvider.saveItem('gestao', {
            title : 'GESTÃO',
            translation: 'GESTAO.GESTAO',
            group : true,
            weight: 1
        });
    }

	angular
		.module('app.gestao', ['app.autenticado'])
		.config(config);
}