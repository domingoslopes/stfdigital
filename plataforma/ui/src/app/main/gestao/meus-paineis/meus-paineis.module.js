var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            'use strict';
            /** @ngInject **/
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/gestao/meus-paineis');
                $stateProvider.state('app.gestao.meus-paineis', {
                    url: '/meus-paineis',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/gestao/meus-paineis/meus-paineis.html',
                            controller: meusPaineis.PaineisController,
                            controllerAs: 'vm'
                        },
                        'principal@app.gestao.meus-paineis': {
                            templateUrl: 'app/main/gestao/meus-paineis/principal/principal.html',
                            controller: meusPaineis.PrincipalController,
                            controllerAs: 'vm'
                        },
                        'peticoes@app.gestao.meus-paineis': {
                            templateUrl: 'app/main/gestao/meus-paineis/peticoes/peticoes.html',
                            controller: meusPaineis.PeticoesController,
                            controllerAs: 'vm'
                        },
                        'autuacoes@app.gestao.meus-paineis': {
                            templateUrl: 'app/main/gestao/meus-paineis/autuacoes/autuacoes.html',
                            controller: meusPaineis.AutuacoesController,
                            controllerAs: 'vm'
                        },
                        'produtividade-do-time@app.gestao.meus-paineis': {
                            templateUrl: 'app/main/gestao/meus-paineis/produtividade-do-time/produtividade-do-time.html',
                            controller: meusPaineis.ProdutividadeDoTimeController,
                            controllerAs: 'vm'
                        }
                    }
                }).state('app.gestao.meus-paineis.principal', {
                    url: '/principal',
                }).state('app.gestao.meus-paineis.peticoes', {
                    url: '/peticoes',
                }).state('app.gestao.meus-paineis.autuacoes', {
                    url: '/autuacoes',
                }).state('app.gestao.meus-paineis.produtividade-do-time', {
                    url: '/produtividade-do-time',
                });
                msNavigationServiceProvider.saveItem('gestao.meus-paineis', {
                    title: 'Meus Paineis',
                    icon: 'icon-view-dashboard',
                    state: 'app.gestao.meus-paineis',
                    translation: 'GESTAO.MEUS-PAINEIS.MEUS-PAINEIS',
                    weight: 1
                });
            }
            angular
                .module('app.gestao.meus-paineis', ['app.gestao'])
                .config(config);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=meus-paineis.module.js.map
