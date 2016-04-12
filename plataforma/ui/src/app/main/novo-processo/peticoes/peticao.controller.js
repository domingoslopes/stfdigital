var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        var peticoes;
        (function (peticoes) {
            'use strict';
            var PeticaoController = (function () {
                function PeticaoController($state, peticaoService) {
                    this.$state = $state;
                    this.peticaoService = peticaoService;
                    this.basicForm = {};
                    this.formWizard = {};
                    this.states = PeticaoController.mockClasses();
                }
                PeticaoController.prototype.sendForm = function () {
                    var _this = this;
                    this.peticaoService.peticionar(PeticaoController.mockPeticao())
                        .then(function () {
                        _this.formWizard = {};
                        _this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
                    });
                };
                PeticaoController.mockClasses = function () {
                    return ('ADI ADO')
                        .split(' ')
                        .map(function (state) { return { abbrev: state }; });
                };
                PeticaoController.mockPeticao = function () {
                    var anexo = {
                        documento: 1,
                        tipo: 1
                    };
                    return {
                        classeId: "ADI",
                        poloAtivo: ["João da Silva"],
                        poloPassivo: ["Maria da Silva"],
                        anexos: [anexo]
                    };
                };
                PeticaoController.$inject = ['$state', 'app.novo-processo.peticoes.PeticaoService'];
                return PeticaoController;
            }());
            peticoes.PeticaoController = PeticaoController;
            angular
                .module('app.novo-processo.peticoes')
                .service('app.novo-processo.peticoes.PeticaoController', PeticaoController);
        })(peticoes = novoProcesso.peticoes || (novoProcesso.peticoes = {}));
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=peticao.controller.js.map
