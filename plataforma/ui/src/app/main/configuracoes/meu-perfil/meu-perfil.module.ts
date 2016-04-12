module app.configuracoes.meuPerfil {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject **/
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider: any) {

        $translatePartialLoaderProvider.addPart('app/main/configuracoes/meu-perfil');

        $stateProvider.state('app.configuracoes.meu-perfil', {
            url : '/meu-perfil',
            views : {
                'content@app.autenticado' : {
                    templateUrl : 'app/main/configuracoes/meu-perfil/meu-perfil.html'
                }
            }
        });

        msNavigationServiceProvider.saveItem('configuracoes.meu-perfil', {
            title : 'Meu Perfil',
            icon : 'icon-account',
            state : 'app.configuracoes.meu-perfil',
            translation : 'CONFIGURACOES.MEUS-PAINEIS.MEUS-PAINEIS',
            weight : 1
        });
    }

    angular
        .module('app.configuracoes.meu-perfil', ['app.configuracoes'])
        .config(config);
}