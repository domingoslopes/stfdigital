var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            var CriteriasSummaryDirective = (function () {
                function CriteriasSummaryDirective() {
                    this.restrict = 'E';
                    this.scope = {
                        criterias: '='
                    };
                    this.templateUrl = 'app/main/processos/pesquisa-avancada/directives/criterias-summary/criterias-summary.html';
                }
                CriteriasSummaryDirective.factory = function () {
                    return function () {
                        return new CriteriasSummaryDirective();
                    };
                };
                return CriteriasSummaryDirective;
            }());
            angular
                .module('app.processos.pesquisa-avancada')
                .directive('criteriasSummary', CriteriasSummaryDirective.factory());
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=criterias-summary.directive.js.map
