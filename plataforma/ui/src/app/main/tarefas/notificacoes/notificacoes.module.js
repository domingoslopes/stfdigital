var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var notificacoes;
        (function (notificacoes) {
            'use strict';
            /** @ngInject **/
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/tarefas/notificacoes');
                $stateProvider
                    .state('app.tarefas.notificacoes', {
                    url: '/notificacoes',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/tarefas/notificacoes/notificacoes.html'
                        }
                    }
                });
                msNavigationServiceProvider.saveItem('tarefas.notificacoes', {
                    title: 'Notificações',
                    icon: 'icon-comment-alert',
                    state: 'app.tarefas.notificacoes',
                    translation: 'TAREFAS.NOTIFICACOES.NOTIFICACOES',
                    weight: 1,
                    badge: {
                        content: 2,
                        color: '#D22E2E'
                    },
                });
            }
            angular
                .module('app.tarefas.notificacoes', ['app.tarefas'])
                .config(config);
        })(notificacoes = tarefas.notificacoes || (tarefas.notificacoes = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=notificacoes.module.js.map
