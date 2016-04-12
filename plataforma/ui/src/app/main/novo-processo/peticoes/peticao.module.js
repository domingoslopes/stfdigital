var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        var peticoes;
        (function (peticoes) {
            'use strict';
            /** @ngInject * */
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
            function config($translatePartialLoaderProvider, $stateProvider) {
                $translatePartialLoaderProvider.addPart('app/main/novo-processo/peticoes');
                $stateProvider.state('app.novo-processo.peticoes', {
                    url: '/peticao',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/novo-processo/peticoes/peticao.html',
                            controller: peticoes.PeticaoController,
                            controllerAs: 'vm'
                        }
                    }
                });
            }
            angular
                .module('app.novo-processo.peticoes', ['app.novo-processo'])
                .config(config);
        })(peticoes = novoProcesso.peticoes || (novoProcesso.peticoes = {}));
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=peticao.module.js.map
