var app;
(function (app) {
    var cadastro;
    (function (cadastro) {
        'use strict';
        /** @ngInject **/
        config.$inject = ["$translatePartialLoaderProvider", "$stateProvider"];
        function config($translatePartialLoaderProvider, $stateProvider) {
            $translatePartialLoaderProvider.addPart('app/main/cadastro');
            $stateProvider.state('app.cadastro', {
                parent: 'app.nao-autenticado',
                url: '/cadastro',
                views: {
                    'form@app.nao-autenticado': {
                        templateUrl: 'app/main/cadastro/cadastro.html',
                        controller: cadastro.CadastroController,
                        controllerAs: 'vm'
                    }
                }
            });
        }
        angular
            .module('app.cadastro', ['app.nao-autenticado', 'ngMask'])
            .config(config);
    })(cadastro = app.cadastro || (app.cadastro = {}));
})(app || (app = {}));

//# sourceMappingURL=cadastro.module.js.map
