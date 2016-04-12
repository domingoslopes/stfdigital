module app.gestao.meusPaineis {

    export class ProdutividadeDoTimeController {

        public widget11: any;

        static $inject = ['app.gestao.meus-paineis.MeusPaineisService'];

        constructor(private meusPaineisService: MeusPaineisService) {
            this.widget11 = meusPaineisService.loadDashboardWidget('Widget11');
        }

    }

    angular
        .module('app.gestao.meus-paineis')
        .controller('app.gestao.meus-paineis.ProdutividadeDoTimeController', ProdutividadeDoTimeController);
}