var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        var peticoesFisicas;
        (function (peticoesFisicas) {
            'use strict';
            /** @ngInject * */
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
            function config($translatePartialLoaderProvider, $stateProvider) {
                $translatePartialLoaderProvider.addPart('app/main/novo-processo/peticoes');
                $stateProvider.state('app.novo-processo.peticoes-fisicas', {
                    url: '/peticao-fisica',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/novo-processo/peticoes-fisicas/peticao-fisica.html',
                            controller: peticoesFisicas.PeticaoFisicaController,
                            controllerAs: 'vm'
                        }
                    }
                });
            }
            angular
                .module('app.novo-processo.peticoes-fisicas', ['app.novo-processo'])
                .config(config);
        })(peticoesFisicas = novoProcesso.peticoesFisicas || (novoProcesso.peticoesFisicas = {}));
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=peticao-fisica.module.js.map
