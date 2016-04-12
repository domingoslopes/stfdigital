module app.processos.ultimosAcessos {
    'use strict';
    import IFilterFilter = angular.IFilterFilter;
    import IStateService = angular.ui.IStateService;
    
    class UltimosAcessosController {
    
        public ultimosAcessosDtOptions: any;
        public ultimosAcessosOrig: Acesso[];
        public activeFilter: number;
        public filterObject: any;
        
        /** @ngInject **/
        constructor(private $filter: IFilterFilter,
                    private $state: IStateService,
                    public ultimosAcessos: Acesso[]) {

            this.ultimosAcessosOrig = angular.copy(ultimosAcessos);
            this.ultimosAcessosDtOptions = this.defineUltimosAcessosDtOptions();
        }

        public foo(): void {
            console.log("bar");
        }

        public setActiveFilter(index): void {
            var newActiveFilter = (index !== this.activeFilter);
            this.activeFilter = index;

            if (newActiveFilter) {
                this.updateFilterObject();
            }
        }

        public isTabActive(stateName, index): boolean {
            var active = this.$state.current.name == stateName;

            if (active) {
                this.setActiveFilter(index);
            }
            return active;
        }

        private updateFilterObject(): void {
            switch (this.activeFilter) {
                case 0:
                    this.filterObject = {};
                    break;
                case 1:
                    this.filterObject = {type: {name: "Petição"}};
                    break;
                case 2:
                    this.filterObject = {type: {name: "Processo"}};
                    break;
                case 3:
                    this.filterObject = {type: {name: "Peça"}};
                    break;
            }
            this.ultimosAcessos = this.$filter(this.ultimosAcessosOrig, this.filterObject);
        }

        private defineUltimosAcessosDtOptions(): any {
            return {
                dom       : '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                pagingType: 'simple',
                autoWidth : true,
                responsive: false,
                searching: false,
                columns: [
                    null,
                    null,
                    null,
                    null,
                    {iDataSort: 5},
                    {bVisible: false},
                    null,
                    null
                ]
            };
        }
    }

    angular
        .module('app.processos.ultimos-acessos')
        .controller('app.processos.ultimos-acessos.UltimosAcessosController', UltimosAcessosController);
}