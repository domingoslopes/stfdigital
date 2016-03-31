(function() {
    'use strict';

    angular.module('app.novo-processo.peticao',
            [ 'app.novo-processo', 'classy' ]).config(config);

    /** @ngInject * */
    function config($translatePartialLoaderProvider, $stateProvider) {
        $translatePartialLoaderProvider
                .addPart('app/main/novo-processo/peticoes');
        $stateProvider
                .state(
                        'app.novo-processo.peticao',
                        {
                            url : '/peticao',
                            views : {
                                'content@app.autenticado' : {
                                    templateUrl : 'app/main/novo-processo/peticoes/peticao.html',
                                    controller : 'FormsController as vm'
                                }
                            }
                        });
    }
})();