var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        var peticoesFisicas;
        (function (peticoesFisicas) {
            'use strict';
            var PeticaoFisicaController = (function () {
                function PeticaoFisicaController($state, peticaoFisicaService) {
                    this.$state = $state;
                    this.peticaoFisicaService = peticaoFisicaService;
                    this.basicForm = {};
                    this.formWizard = {};
                    this.states = PeticaoFisicaController.mockClasses();
                }
                PeticaoFisicaController.prototype.sendForm = function () {
                    var _this = this;
                    this.peticaoFisicaService.registrar(PeticaoFisicaController.mockPeticao())
                        .then(function () {
                        _this.formWizard = {};
                        _this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
                    });
                };
                PeticaoFisicaController.mockClasses = function () {
                    return ('ADI ADO')
                        .split(' ')
                        .map(function (state) { return { abbrev: state }; });
                };
                PeticaoFisicaController.mockPeticao = function () {
                    return { formaRecebimento: peticoesFisicas.FormaRecebimento.SEDEX,
                        volumes: 1,
                        apensos: 1,
                        numeroSedex: "SR123456789BR",
                        tipoProcesso: "originario" };
                };
                PeticaoFisicaController.$inject = ['$state', 'app.novo-processo.peticoes-fisicas.PeticaoFisicaService'];
                return PeticaoFisicaController;
            }());
            peticoesFisicas.PeticaoFisicaController = PeticaoFisicaController;
            angular
                .module('app.novo-processo.peticoes-fisicas')
                .service('app.novo-processo.peticoes-fisicas.PeticaoFisicaController', PeticaoFisicaController);
        })(peticoesFisicas = novoProcesso.peticoesFisicas || (novoProcesso.peticoesFisicas = {}));
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=peticao-fisica.controller.js.map
