var app;
(function (app) {
    var configuracoes;
    (function (configuracoes) {
        var meuPerfil;
        (function (meuPerfil) {
            'use strict';
            /** @ngInject **/
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/configuracoes/meu-perfil');
                $stateProvider.state('app.configuracoes.meu-perfil', {
                    url: '/meu-perfil',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/configuracoes/meu-perfil/meu-perfil.html'
                        }
                    }
                });
                msNavigationServiceProvider.saveItem('configuracoes.meu-perfil', {
                    title: 'Meu Perfil',
                    icon: 'icon-account',
                    state: 'app.configuracoes.meu-perfil',
                    translation: 'CONFIGURACOES.MEUS-PAINEIS.MEUS-PAINEIS',
                    weight: 1
                });
            }
            angular
                .module('app.configuracoes.meu-perfil', ['app.configuracoes'])
                .config(config);
        })(meuPerfil = configuracoes.meuPerfil || (configuracoes.meuPerfil = {}));
    })(configuracoes = app.configuracoes || (app.configuracoes = {}));
})(app || (app = {}));

//# sourceMappingURL=meu-perfil.module.js.map
