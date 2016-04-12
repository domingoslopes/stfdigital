module app.configuracoes {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject **/
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider: any) {

        $translatePartialLoaderProvider.addPart('app/main/configuracoes');

        $stateProvider.state('app.configuracoes', {
            abstract : true,
            url : '/configuracoes',
            parent : 'app.autenticado'
        });

        // Navigation
        msNavigationServiceProvider.saveItem('configuracoes', {
            title : 'CONFIGURAÇÕES',
            translation : 'CONFIGURACOES.CONFIGURACOES',
            group : true,
            weight : 1
        });
    }

    angular
        .module('app.configuracoes', ['app.autenticado'])
        .config(config);
}