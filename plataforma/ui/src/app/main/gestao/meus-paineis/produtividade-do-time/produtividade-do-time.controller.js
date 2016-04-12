var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            var ProdutividadeDoTimeController = (function () {
                function ProdutividadeDoTimeController(meusPaineisService) {
                    this.meusPaineisService = meusPaineisService;
                    this.widget11 = meusPaineisService.loadDashboardWidget('Widget11');
                }
                ProdutividadeDoTimeController.$inject = ['app.gestao.meus-paineis.MeusPaineisService'];
                return ProdutividadeDoTimeController;
            }());
            meusPaineis.ProdutividadeDoTimeController = ProdutividadeDoTimeController;
            angular
                .module('app.gestao.meus-paineis')
                .controller('app.gestao.meus-paineis.ProdutividadeDoTimeController', ProdutividadeDoTimeController);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=produtividade-do-time.controller.js.map
