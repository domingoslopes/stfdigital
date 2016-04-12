var app;
(function (app) {
    var naoAutenticado;
    (function (naoAutenticado) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
        function config($translatePartialLoaderProvider, $stateProvider) {
            $translatePartialLoaderProvider.addPart('app/main/nao-autenticado');
            $stateProvider
                .state('app.nao-autenticado', {
                abstract: true,
                views: {
                    'main@': {
                        templateUrl: 'app/core/layouts/content-only.html',
                        controller: app.MainController,
                        controllerAs: 'vm'
                    },
                    'content@app.nao-autenticado': {
                        templateUrl: 'app/main/nao-autenticado/nao-autenticado.html'
                    }
                }
            });
        }
        angular
            .module('app.nao-autenticado', [])
            .config(config);
    })(naoAutenticado = app.naoAutenticado || (app.naoAutenticado = {}));
})(app || (app = {}));

//# sourceMappingURL=nao-autenticado.module.js.map
