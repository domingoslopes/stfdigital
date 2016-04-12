var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            'use strict';
            var PeticoesController = (function () {
                function PeticoesController(meusPaineisService) {
                    this.meusPaineisService = meusPaineisService;
                    this.chart1 = meusPaineisService.loadDashboardWidget('Chart1');
                    this.chart2 = meusPaineisService.loadDashboardWidget('Chart2');
                }
                PeticoesController.$inject = ['app.gestao.meus-paineis.MeusPaineisService'];
                return PeticoesController;
            }());
            meusPaineis.PeticoesController = PeticoesController;
            angular
                .module('app.gestao.meus-paineis')
                .controller('app.gestao.meus-paineis.PeticoesController', PeticoesController);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=peticoes.controller.js.map
