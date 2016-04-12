var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            var ComparisionOperator = (function () {
                function ComparisionOperator() {
                }
                ComparisionOperator.IGUAL = 'IGUAL';
                ComparisionOperator.CONTEM = 'CONTEM';
                ComparisionOperator.ENTRE = 'ENTRE';
                ComparisionOperator.MAIORQUE = 'MAIOR-QUE';
                ComparisionOperator.MENORQUE = 'MENOR-QUE';
                ComparisionOperator.EXISTE = 'EXISTE';
                return ComparisionOperator;
            }());
            pesquisaAvancada.ComparisionOperator = ComparisionOperator;
            var SearchBuilderDirective = (function () {
                function SearchBuilderDirective() {
                    this.restrict = 'E';
                    this.templateUrl = 'app/main/processos/pesquisa-avancada/directives/search-builder/search-builder.html';
                    this.scope = {
                        traits: '=',
                        search: '='
                    };
                    this.controller = pesquisaAvancada.SearchBuilderController;
                    this.controllerAs = 'vm';
                }
                SearchBuilderDirective.factory = function () {
                    return function () {
                        return new SearchBuilderDirective();
                    };
                };
                return SearchBuilderDirective;
            }());
            angular
                .module('app.processos.pesquisa-avancada')
                .directive('searchBuilder', SearchBuilderDirective.factory());
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=search-builder.directive.js.map
