var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var minhasTarefas;
        (function (minhasTarefas) {
            'use strict';
            /** @ngInject * */
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/tarefas/minhas-tarefas');
                $stateProvider.state('app.tarefas.minhas-tarefas', {
                    url: '/minhas-tarefas',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/tarefas/minhas-tarefas/minhas-tarefas.html',
                            controller: minhasTarefas.MinhasTarefasController,
                            controllerAs: 'vm'
                        }
                    },
                    resolve: {
                        tasks: ['app.tarefas.minhas-tarefas.MinhasTarefasService', function (minhasTarefasService) {
                                return minhasTarefasService.get();
                            }],
                        tags: ['app.tarefas.minhas-tarefas.RotulosService', function (rotulosService) {
                                return rotulosService.get();
                            }]
                    }
                });
                msNavigationServiceProvider.saveItem('tarefas.minhas-tarefas', {
                    title: 'Minhas Tarefas',
                    icon: 'icon-view-list',
                    state: 'app.tarefas.minhas-tarefas',
                    translation: 'TAREFAS.MEUS-PAINEIS.MEUS-PAINEIS',
                    weight: 1,
                    badge: {
                        content: 1,
                        color: '#03A9F3'
                    }
                });
            }
            angular
                .module('app.tarefas.minhas-tarefas', ['app.tarefas'])
                .config(config);
        })(minhasTarefas = tarefas.minhasTarefas || (tarefas.minhasTarefas = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=minhas-tarefas.module.js.map
