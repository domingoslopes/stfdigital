var app;
(function (app) {
    var cadastro;
    (function (cadastro) {
        'use strict';
        var CadastroController = (function () {
            function CadastroController() {
                this.form.tipoCadastro = 1;
            }
            CadastroController.prototype.cadastrar = function () {
                console.log("TODO: Fazer cadastro. Detalhes do usuário: ", this.form);
            };
            return CadastroController;
        }());
        cadastro.CadastroController = CadastroController;
        angular
            .module('app.cadastro')
            .controller('CadastroController', CadastroController);
    })(cadastro = app.cadastro || (app.cadastro = {}));
})(app || (app = {}));

//# sourceMappingURL=cadastro.controller.js.map
