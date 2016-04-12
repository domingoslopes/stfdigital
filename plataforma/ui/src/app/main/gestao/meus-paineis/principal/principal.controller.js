var app;
(function (app) {
    var gestao;
    (function (gestao) {
        var meusPaineis;
        (function (meusPaineis) {
            'use strict';
            var PrincipalController = (function () {
                function PrincipalController(meusPaineisService) {
                    this.meusPaineisService = meusPaineisService;
                    this.widget1 = meusPaineisService.loadDashboardWidget('Widget1');
                    this.widget2 = meusPaineisService.loadDashboardWidget('Widget2');
                    this.widget3 = meusPaineisService.loadDashboardWidget('Widget3');
                    this.widget4 = meusPaineisService.loadDashboardWidget('Widget4');
                    this.widget5 = meusPaineisService.loadDashboardWidget('Widget5');
                    this.widget6 = meusPaineisService.loadDashboardWidget('Widget6');
                    this.widget7 = meusPaineisService.loadDashboardWidget('Widget7');
                }
                PrincipalController.$inject = ['app.gestao.meus-paineis.MeusPaineisService'];
                return PrincipalController;
            }());
            meusPaineis.PrincipalController = PrincipalController;
            angular
                .module('app.gestao.meus-paineis')
                .controller('app.gestao.meus-paineis.PrincipalController', PrincipalController);
        })(meusPaineis = gestao.meusPaineis || (gestao.meusPaineis = {}));
    })(gestao = app.gestao || (app.gestao = {}));
})(app || (app = {}));

//# sourceMappingURL=principal.controller.js.map
