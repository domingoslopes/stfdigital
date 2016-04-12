module app.novoProcesso.peticoes {
    'use strict';
    import IStateService = angular.ui.IStateService;

    export class PeticaoController {

        public basicForm: Object = { };
        public formWizard: Object = { };
        public states: Object[] = PeticaoController.mockClasses();

        static $inject = ['$state', 'app.novo-processo.peticoes.PeticaoService'];

        constructor(private $state: IStateService,
                    private peticaoService: PeticaoService) { }

        public sendForm(): void {
            this.peticaoService.peticionar(PeticaoController.mockPeticao())
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

        private static mockPeticao(): IPeticao {
            
            var anexo : IAnexo = <IAnexo>{
                documento: 1,
                tipo:1
            };
            
            return <IPeticao>{
                classeId: "ADI",
                poloAtivo: ["João da Silva"],
                poloPassivo: ["Maria da Silva"],
                anexos: [anexo]
            };
        }
    }

    angular
        .module('app.novo-processo.peticoes')
        .service('app.novo-processo.peticoes.PeticaoController', PeticaoController);
}