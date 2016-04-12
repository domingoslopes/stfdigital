var app;
(function (app) {
    var gestao;
    (function (gestao) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
        function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
            $translatePartialLoaderProvider.addPart('app/main/gestao');
            $stateProvider
                .state('app.gestao', {
                abstract: true,
                url: '/gestao',
                parent: 'app.autenticado',
            });
            // Navigation
            msNavigationServiceProvider.saveItem('gestao', {
                title: 'GESTÃO',
                translation: 'GESTAO.GESTAO',
                group: true,
                weight: 1
            });
        }
        angular
            .module('app.gestao', ['app.autenticado'])
            .config(config);
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=gestao.module.js.map
