module app.novoProcesso.peticoes {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject * */
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider) {

        $translatePartialLoaderProvider.addPart('app/main/novo-processo/peticoes');

        $stateProvider.state('app.novo-processo.peticoes', {
            url : '/peticao',
            views : {
                'content@app.autenticado' : {
                    templateUrl : 'app/main/novo-processo/peticoes/peticao.html',
                    controller : PeticaoController,
                    controllerAs: 'vm'
                }
            }
        });
    }

    angular
        .module('app.novo-processo.peticoes', ['app.novo-processo'] )
        .config(config);
}