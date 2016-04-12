var app;
(function (app) {
    var processos;
    (function (processos) {
        var ultimosAcessos;
        (function (ultimosAcessos) {
            'use strict';
            /** @ngInject **/
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/processos/ultimos-acessos');
                $stateProvider
                    .state('app.processos.ultimos-acessos', {
                    url: '/ultimos-acessos',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/processos/ultimos-acessos/ultimos-acessos.html',
                            controller: 'app.processos.ultimos-acessos.UltimosAcessosController',
                            controllerAs: 'vm'
                        }
                    },
                    resolve: {
                        ultimosAcessos: ['app.processos.ultimos-acessos.UltimosAcessosService', function (ultimosAcessosService) {
                                return ultimosAcessosService.acessos();
                            }]
                    }
                })
                    .state('app.processos.ultimos-acessos.todos', {
                    url: '/todos'
                })
                    .state('app.processos.ultimos-acessos.peticoes', {
                    url: '/peticoes'
                })
                    .state('app.processos.ultimos-acessos.processos', {
                    url: '/processos'
                })
                    .state('app.processos.ultimos-acessos.pecas', {
                    url: '/pecas'
                });
                msNavigationServiceProvider.saveItem('processos.ultimos-acessos', {
                    title: 'Últimos Acessos',
                    icon: 'icon-history',
                    state: 'app.processos.ultimos-acessos',
                    translation: 'PROCESSOS.ULTIMOS-ACESSOS.ULTIMOS-ACESSOS',
                    weight: 1
                });
            }
            angular
                .module('app.processos.ultimos-acessos', ['app.processos'])
                .config(config);
        })(ultimosAcessos = processos.ultimosAcessos || (processos.ultimosAcessos = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=ultimos-acessos.module.js.map
