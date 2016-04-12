var app;
(function (app) {
    var processos;
    (function (processos) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
        function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
            $translatePartialLoaderProvider.addPart('app/main/processos');
            $stateProvider
                .state('app.processos', {
                abstract: true,
                url: '/processos',
                parent: 'app.autenticado',
            });
            // Navigation
            msNavigationServiceProvider.saveItem('processos', {
                title: 'PROCESSOS',
                translation: 'PROCESSOS.PROCESSOS',
                group: true,
                weight: 1
            });
        }
        angular
            .module('app.processos', ['app.autenticado'])
            .config(config);
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=processos.module.js.map
