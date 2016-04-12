var app;
(function (app) {
    var autenticado;
    (function (autenticado) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
        function config($translatePartialLoaderProvider, $stateProvider) {
            $translatePartialLoaderProvider.addPart('app/main/autenticado');
            $stateProvider.state('app.autenticado', {
                abstract: true,
                views: {
                    'main@': {
                        templateUrl: 'app/core/layouts/vertical-navigation.html',
                        controller: 'app.MainController',
                        controllerAs: 'vm'
                    },
                    'toolbar@app.autenticado': {
                        templateUrl: 'app/toolbar/toolbar.html',
                        controller: 'ToolbarController',
                        controllerAs: 'vm'
                    },
                    'navigation@app.autenticado': {
                        templateUrl: 'app/navigation/navigation.html',
                        controller: 'NavigationController',
                        controllerAs: 'vm'
                    },
                }
            });
        }
        angular
            .module('app.autenticado', [])
            .config(config);
    })(autenticado = app.autenticado || (app.autenticado = {}));
})(app || (app = {}));

//# sourceMappingURL=autenticado.module.js.map
