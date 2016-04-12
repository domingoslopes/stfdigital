var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        'use strict';
        /** @ngInject * */
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
        function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
            $translatePartialLoaderProvider.addPart('app/main/tarefas');
            $stateProvider.state('app.tarefas', {
                abstract: true,
                url: '/tarefas',
                parent: 'app.autenticado'
            });
            // Navigation
            msNavigationServiceProvider.saveItem('tarefas', {
                title: 'TAREFAS',
                translation: 'TAREFAS.TAREFAS',
                group: true,
                weight: 1
            });
        }
        angular
            .module('app.tarefas', ['app.autenticado'])
            .config(config);
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=tarefas.module.js.map
