module app.novoProcesso.peticoesFisicas {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject * */
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider) {

        $translatePartialLoaderProvider.addPart('app/main/novo-processo/peticoes');

        $stateProvider.state('app.novo-processo.peticoes-fisicas', {
            url : '/peticao-fisica',
            views : {
                'content@app.autenticado' : {
                    templateUrl : 'app/main/novo-processo/peticoes-fisicas/peticao-fisica.html',
                    controller : PeticaoFisicaController,
                    controllerAs: 'vm'
                }
            }
        });
    }

    angular
        .module('app.novo-processo.peticoes-fisicas', ['app.novo-processo'])
        .config(config);
}