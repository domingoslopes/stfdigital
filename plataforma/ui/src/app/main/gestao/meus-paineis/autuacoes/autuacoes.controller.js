var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            'use strict';
            var AutuacoesController = (function () {
                function AutuacoesController(meusPaineisService) {
                    this.meusPaineisService = meusPaineisService;
                    this.widget8 = meusPaineisService.loadDashboardWidget('Widget8');
                    this.widget9 = meusPaineisService.loadDashboardWidget('Widget9');
                    this.widget10 = meusPaineisService.loadDashboardWidget('Widget10');
                }
                AutuacoesController.$inject = ['app.gestao.meus-paineis.MeusPaineisService'];
                return AutuacoesController;
            }());
            meusPaineis.AutuacoesController = AutuacoesController;
            angular
                .module('app.gestao.meus-paineis')
                .controller('app.gestao.meus-paineis.AutuacoesController', AutuacoesController);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=autuacoes.controller.js.map
