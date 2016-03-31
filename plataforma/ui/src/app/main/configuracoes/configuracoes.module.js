(function() {
    'use strict';

    angular.module('app.configuracoes', [ 'app.autenticado', 'classy' ])
            .config(config);

    /** @ngInject * */
    function config($translatePartialLoaderProvider, $stateProvider,
            msNavigationServiceProvider) {
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
})();