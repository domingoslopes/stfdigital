module app.gestao.meusPaineis {
    'use strict';

    export class PeticoesController {

        public chart1: any;
        public chart2: any;

        static $inject = ['app.gestao.meus-paineis.MeusPaineisService'];
        
        constructor(private meusPaineisService: MeusPaineisService) {
            this.chart1 = meusPaineisService.loadDashboardWidget('Chart1');
            this.chart2 = meusPaineisService.loadDashboardWidget('Chart2');
        }
    }
    
    angular
        .module('app.gestao.meus-paineis')
        .controller('app.gestao.meus-paineis.PeticoesController', PeticoesController);
}