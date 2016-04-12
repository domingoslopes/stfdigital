var app;
(function (app) {
    var configuracoes;
    (function (configuracoes) {
        var administracao;
        (function (administracao) {
            'use strict';
            /** @ngInject **/
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/configuracoes/administracao');
                $stateProvider.state('app.configuracoes.administracao', {
                    url: '/administracao',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/configuracoes/administracao/administracao.html'
                        }
                    }
                });
                msNavigationServiceProvider.saveItem('configuracoes.administracao', {
                    title: 'Administração',
                    icon: 'icon-cog',
                    state: 'app.configuracoes.administracao',
                    translation: 'CONFIGURACOES.ADMINISTRACAO.ADMINISTRACAO',
                    weight: 1
                });
            }
            angular
                .module('app.configuracoes.administracao', ['app.configuracoes'])
                .config(config);
        })(administracao = configuracoes.administracao || (configuracoes.administracao = {}));
    })(configuracoes = app.configuracoes || (app.configuracoes = {}));
})(app || (app = {}));

//# sourceMappingURL=administracao.module.js.map
