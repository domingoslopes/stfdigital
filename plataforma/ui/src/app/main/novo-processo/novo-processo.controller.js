var app;
(function (app) {
    var novoProcesso;
    (function (novoProcesso) {
        'use strict';
        var NovoProcessoController = (function () {
            /** @ngInject **/
            NovoProcessoController.$inject = ["$scope"];
            function NovoProcessoController($scope) {
                var _this = this;
                this.$scope = $scope;
                this.buscaProcesso = "";
                this.$scope.$watch(function () { return _this.buscaProcesso; }, this.filtrarProcessos());
                this.processos = NovoProcessoController.getProcessosMock();
            }
            NovoProcessoController.prototype.filtrarProcessos = function () {
                var _this = this;
                return function () {
                    var busca = _this.buscaProcesso.toLowerCase();
                    var origem = NovoProcessoController.getProcessosMock();
                    if (busca.length === 0) {
                        _this.processos = origem;
                    }
                    else {
                        _this.processos = origem.filter(function (processo) {
                            return (processo.name.toLowerCase().indexOf(busca) !== -1);
                        });
                    }
                };
            };
            NovoProcessoController.getProcessosMock = function () {
                return [{
                        id: 1,
                        name: "Nova Petição",
                        state: 'app.novo-processo.peticoes'
                    }, {
                        id: 2,
                        name: "Nova Petição Física",
                        state: 'app.novo-processo.peticoes-fisicas'
                    }];
            };
            return NovoProcessoController;
        }());
        novoProcesso.NovoProcessoController = NovoProcessoController;
        angular
            .module('app.novo-processo')
            .controller('app.novo-processo.NovoProcessoController', NovoProcessoController);
    })(novoProcesso = app.novoProcesso || (app.novoProcesso = {}));
})(app || (app = {}));

//# sourceMappingURL=novo-processo.controller.js.map
