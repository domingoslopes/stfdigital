var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        'use strict';
        /** @ngInject * */
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
        function config($translatePartialLoaderProvider, $stateProvider) {
            $translatePartialLoaderProvider.addPart('app/main/novo-processo');
            $stateProvider.state('app.novo-processo', {
                parent: 'app.autenticado',
                url: '/novo-processo',
                views: {
                    'content@app.autenticado': {
                        templateUrl: 'app/main/novo-processo/novo-processo.html',
                        controller: novoProcesso.NovoProcessoController,
                        controllerAs: 'vm'
                    }
                }
            });
        }
        angular
            .module('app.novo-processo', ['app.autenticado'])
            .config(config);
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=novo-processo.module.js.map
