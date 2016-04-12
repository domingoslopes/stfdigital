var app;
(function (app) {
    var processos;
    (function (processos) {
        var ultimosAcessos;
        (function (ultimosAcessos_1) {
            'use strict';
            var UltimosAcessosController = (function () {
                /** @ngInject **/
                UltimosAcessosController.$inject = ["$filter", "$state", "ultimosAcessos"];
                function UltimosAcessosController($filter, $state, ultimosAcessos) {
                    this.$filter = $filter;
                    this.$state = $state;
                    this.ultimosAcessos = ultimosAcessos;
                    this.ultimosAcessosOrig = angular.copy(ultimosAcessos);
                    this.ultimosAcessosDtOptions = this.defineUltimosAcessosDtOptions();
                }
                UltimosAcessosController.prototype.foo = function () {
                    console.log("bar");
                };
                UltimosAcessosController.prototype.setActiveFilter = function (index) {
                    var newActiveFilter = (index !== this.activeFilter);
                    this.activeFilter = index;
                    if (newActiveFilter) {
                        this.updateFilterObject();
                    }
                };
                UltimosAcessosController.prototype.isTabActive = function (stateName, index) {
                    var active = this.$state.current.name == stateName;
                    if (active) {
                        this.setActiveFilter(index);
                    }
                    return active;
                };
                UltimosAcessosController.prototype.updateFilterObject = function () {
                    switch (this.activeFilter) {
                        case 0:
                            this.filterObject = {};
                            break;
                        case 1:
                            this.filterObject = { type: { name: "Petição" } };
                            break;
                        case 2:
                            this.filterObject = { type: { name: "Processo" } };
                            break;
                        case 3:
                            this.filterObject = { type: { name: "Peça" } };
                            break;
                    }
                    this.ultimosAcessos = this.$filter(this.ultimosAcessosOrig, this.filterObject);
                };
                UltimosAcessosController.prototype.defineUltimosAcessosDtOptions = function () {
                    return {
                        dom: '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                        pagingType: 'simple',
                        autoWidth: true,
                        responsive: false,
                        searching: false,
                        columns: [
                            null,
                            null,
                            null,
                            null,
                            { iDataSort: 5 },
                            { bVisible: false },
                            null,
                            null
                        ]
                    };
                };
                return UltimosAcessosController;
            }());
            angular
                .module('app.processos.ultimos-acessos')
                .controller('app.processos.ultimos-acessos.UltimosAcessosController', UltimosAcessosController);
        })(ultimosAcessos = processos.ultimosAcessos || (processos.ultimosAcessos = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=ultimos-acessos.controller.js.map
