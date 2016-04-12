var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var painelDeFases;
        (function (painelDeFases) {
            'use strict';
            var PainelDeFasesController = (function () {
                /** @ngInject **/
                PainelDeFasesController.$inject = ["boardList"];
                function PainelDeFasesController(boardList) {
                    this.boardList = boardList;
                }
                return PainelDeFasesController;
            }());
            painelDeFases.PainelDeFasesController = PainelDeFasesController;
            angular
                .module('app.tarefas.painel-de-fases')
                .controller('app.tarefas.painel-de-fases.PaineDeFasesController', PainelDeFasesController);
        })(painelDeFases = tarefas.painelDeFases || (tarefas.painelDeFases = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=painel-de-fases.controller.js.map
