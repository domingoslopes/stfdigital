(function() {
    'use strict';

    angular.module('app.novo-processo.peticao-fisica',
            [ 'app.novo-processo', 'classy' ]).config(config);

    /** @ngInject * */
    function config($translatePartialLoaderProvider, $stateProvider) {
        $translatePartialLoaderProvider
                .addPart('app/main/novo-processo/peticoes');
        $stateProvider
                .state(
                        'app.novo-processo.peticao-fisica',
                        {
                            url : '/peticao-fisica',
                            views : {
                                'content@app.autenticado' : {
                                    templateUrl : 'app/main/novo-processo/peticoes-fisicas/peticao-fisica.html',
                                    controller : 'PeticaoFisicaController as vm'
                                }
                            }
                        });

    }
})();