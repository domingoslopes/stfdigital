module app.tarefas.painelDeFases {
    'use strict';    

    export class PainelDeFasesController {
        
        /** @ngInject **/
        constructor(private boardList) {
            
        }
    }
    
    angular
        .module('app.tarefas.painel-de-fases')
        .controller('app.tarefas.painel-de-fases.PaineDeFasesController', PainelDeFasesController);
}