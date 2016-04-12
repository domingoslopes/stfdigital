var app;
(function (app) {
    var login;
    (function (login) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
        function config($translatePartialLoaderProvider, $stateProvider) {
            $translatePartialLoaderProvider.addPart('app/main/login');
            $stateProvider
                .state('app.login', {
                parent: 'app.nao-autenticado',
                url: '/login',
                views: {
                    'form@app.nao-autenticado': {
                        templateUrl: 'app/main/login/login.html',
                        controller: login.LoginController,
                        controllerAs: 'vm'
                    }
                }
            });
        }
        angular
            .module('app.login', ['app.nao-autenticado'])
            .config(config);
    })(login = app.login || (app.login = {}));
})(app || (app = {}));

//# sourceMappingURL=login.module.js.map
