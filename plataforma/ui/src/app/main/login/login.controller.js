var app;
(function (app) {
    var login;
    (function (login) {
        'use strict';
        var LoginController = (function () {
            /** @ngInject **/
            LoginController.$inject = ["$state"];
            function LoginController($state) {
                this.$state = $state;
            }
            LoginController.prototype.entrar = function () {
                console.log("TODO: Fazer login. Detalhes do usuário: ", this.form);
                this.$state.go('app.tarefas.minhas-tarefas');
            };
            return LoginController;
        }());
        login.LoginController = LoginController;
        angular
            .module('app.login')
            .controller('app.login.LoginController', LoginController);
    })(login = app.login || (app.login = {}));
})(app || (app = {}));

//# sourceMappingURL=login.controller.js.map
