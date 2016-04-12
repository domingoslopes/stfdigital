module app.gestao.meusPaineis {
    'use strict';

    export class AutuacoesController {

        public widget8: any;
        public widget9: any;
        public widget10: any;

        static $inject = ['app.gestao.meus-paineis.MeusPaineisService'];
        
        constructor(private meusPaineisService: MeusPaineisService) {
            this.widget8 = meusPaineisService.loadDashboardWidget('Widget8');
            this.widget9 = meusPaineisService.loadDashboardWidget('Widget9');
            this.widget10 = meusPaineisService.loadDashboardWidget('Widget10');
        }
    }

    angular
        .module('app.gestao.meus-paineis')
        .controller('app.gestao.meus-paineis.AutuacoesController', AutuacoesController);
}