var app;
(function (app) {
    var configuracoes;
    (function (configuracoes) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
        function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
            $translatePartialLoaderProvider.addPart('app/main/configuracoes');
            $stateProvider.state('app.configuracoes', {
                abstract: true,
                url: '/configuracoes',
                parent: 'app.autenticado'
            });
            // Navigation
            msNavigationServiceProvider.saveItem('configuracoes', {
                title: 'CONFIGURAÇÕES',
                translation: 'CONFIGURACOES.CONFIGURACOES',
                group: true,
                weight: 1
            });
        }
        angular
            .module('app.configuracoes', ['app.autenticado'])
            .config(config);
    })(configuracoes = app.configuracoes || (app.configuracoes = {}));
})(app || (app = {}));

//# sourceMappingURL=configuracoes.module.js.map
