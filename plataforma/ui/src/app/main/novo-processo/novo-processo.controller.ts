module app.novoProcesso {
    'use strict';
    
    import IScope = angular.IScope;
    
    interface IProcessoWorkflow {
        id: number;
        name: string;
        state: string;
    }
    
    export class NovoProcessoController {
        
        public buscaProcesso: string;
        public processos: IProcessoWorkflow[];
        
        /** @ngInject **/
        constructor(private $scope: IScope) {
            this.buscaProcesso = "";
            this.$scope.$watch(() => { return this.buscaProcesso; }, this.filtrarProcessos());
            this.processos = NovoProcessoController.getProcessosMock();
        }

        private filtrarProcessos(): Function {
            return (): void => {
                var busca:any = this.buscaProcesso.toLowerCase();
                var origem:IProcessoWorkflow[] = NovoProcessoController.getProcessosMock();

                if (busca.length === 0) {
                    this.processos = origem;
                } else {
                    this.processos = origem.filter((processo) => {
                        return (processo.name.toLowerCase().indexOf(busca) !== -1);
                    });
                }
            };
        }

        private static getProcessosMock(): IProcessoWorkflow[] {
            return [{
                id : 1,
                name : "Nova Petição",
                state : 'app.novo-processo.peticoes'
            }, {
                id : 2,
                name : "Nova Petição Física",
                state : 'app.novo-processo.peticoes-fisicas'
            }];
        }
    }

    angular
        .module('app.novo-processo')
        .controller('app.novo-processo.NovoProcessoController', NovoProcessoController);
}