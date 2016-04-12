module app.novoProcesso.peticoesFisicas {
    'use strict';
    import IStateService = angular.ui.IStateService;
    
    export class PeticaoFisicaController {

        public basicForm: Object = {};
        public formWizard: Object = {};
        public states: Object[] = PeticaoFisicaController.mockClasses();

        static $inject = ['$state', 'app.novo-processo.peticoes-fisicas.PeticaoFisicaService'];
        
        constructor(private $state: IStateService,
                    private peticaoFisicaService: PeticaoFisicaService) { }

        public sendForm(): void {
            this.peticaoFisicaService.registrar(PeticaoFisicaController.mockPeticao())
                .then(() => {
                    this.formWizard = {};
                    this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
            });
        }

        private static mockClasses(): Object[] {
            return ('ADI ADO')
                .split(' ')
                .map(state => { return {abbrev: state}; });
        }

        private static mockPeticao(): PeticaoFisica {
            return { formaRecebimento: FormaRecebimento.SEDEX,
                     volumes: 1,
                     apensos: 1,
                     numeroSedex: "SR123456789BR",
                     tipoProcesso: "originario" };
        }
    }

    angular
        .module('app.novo-processo.peticoes-fisicas')
        .service('app.novo-processo.peticoes-fisicas.PeticaoFisicaController', PeticaoFisicaController);
}