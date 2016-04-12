module app.gestao.meusPaineis {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;
    
    /** @ngInject **/
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider: any) {
            
        $translatePartialLoaderProvider.addPart('app/main/gestao/meus-paineis');

        $stateProvider.state('app.gestao.meus-paineis', {
            url: '/meus-paineis',
            views: {
                'content@app.autenticado': {
                    templateUrl: 'app/main/gestao/meus-paineis/meus-paineis.html',
                    controller: PaineisController,
                    controllerAs: 'vm'
                },
                'principal@app.gestao.meus-paineis': {
                    templateUrl: 'app/main/gestao/meus-paineis/principal/principal.html',
                    controller: PrincipalController,
                    controllerAs: 'vm'
                },
                'peticoes@app.gestao.meus-paineis': {
                    templateUrl: 'app/main/gestao/meus-paineis/peticoes/peticoes.html',
                    controller: PeticoesController,
                    controllerAs: 'vm'
                },
                'autuacoes@app.gestao.meus-paineis': {
                    templateUrl: 'app/main/gestao/meus-paineis/autuacoes/autuacoes.html',
                    controller: AutuacoesController,
                    controllerAs: 'vm'
                },
                'produtividade-do-time@app.gestao.meus-paineis': {
                    templateUrl: 'app/main/gestao/meus-paineis/produtividade-do-time/produtividade-do-time.html',
                    controller: ProdutividadeDoTimeController,
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
            title      : 'Meus Paineis',
            icon       : 'icon-view-dashboard',
            state      : 'app.gestao.meus-paineis',
            translation: 'GESTAO.MEUS-PAINEIS.MEUS-PAINEIS',
            weight     : 1
        });
    }

    angular
        .module('app.gestao.meus-paineis', ['app.gestao'])
        .config(config);
}