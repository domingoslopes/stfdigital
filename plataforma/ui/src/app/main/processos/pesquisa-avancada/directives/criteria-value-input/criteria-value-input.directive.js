var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            var CriteriaValueInputDirective = (function () {
                function CriteriaValueInputDirective() {
                    this.restrict = 'E';
                    this.templateUrl = 'app/main/processos/pesquisa-avancada/directives/criteria-value-input/criteria-value-input.html';
                    this.scope = {
                        criteria: "="
                    };
                    this.link = function () {
                        return function ($scope, el, attrs) {
                            $scope.$watch('criteria.value', function () {
                                CriteriaValueInputDirective.updateCriteriaValidity($scope.criteria);
                            });
                            $scope.$watch('criteria.value[0]', function () {
                                CriteriaValueInputDirective.updateCriteriaValidity($scope.criteria);
                            });
                            $scope.$watch('criteria.value[1]', function () {
                                CriteriaValueInputDirective.updateCriteriaValidity($scope.criteria);
                            });
                            $scope.$watch('criteria.comparisonOperator', function (op) {
                                var criteria = $scope.criteria, value = criteria.value;
                                if (op == pesquisaAvancada.ComparisionOperator.ENTRE) {
                                    criteria.value = _.isArray(value) ? value : [value];
                                }
                                else {
                                    criteria.value = _.isArray(value) ? value[0] : value;
                                }
                                CriteriaValueInputDirective.updateCriteriaValidity(criteria);
                            });
                        };
                    };
                }
                CriteriaValueInputDirective.updateCriteriaValidity = function (criteria) {
                    switch (criteria.comparisonOperator) {
                        case pesquisaAvancada.ComparisionOperator.IGUAL:
                        case pesquisaAvancada.ComparisionOperator.CONTEM:
                        case pesquisaAvancada.ComparisionOperator.MAIORQUE:
                        case pesquisaAvancada.ComparisionOperator.MENORQUE:
                            if (criteria.trait.dataType == 'date') {
                                criteria.valid = (!!criteria.value);
                            }
                            else {
                                criteria.valid = ((!!criteria.value) && (criteria.value.length > 0));
                            }
                            break;
                        case pesquisaAvancada.ComparisionOperator.ENTRE:
                            if (criteria.trait.dataType == 'date') {
                                criteria.valid = ((!!criteria.value[0]) && (!!criteria.value[1]));
                            }
                            else {
                                criteria.valid = ((!!criteria.value[0]) && (!!criteria.value[1]) && (criteria.value[0].length > 0) && (criteria.value[1].length > 0));
                            }
                            break;
                        case pesquisaAvancada.ComparisionOperator.EXISTE:
                            criteria.valid = true;
                            break;
                    }
                };
                CriteriaValueInputDirective.factory = function () {
                    return function () {
                        return new CriteriaValueInputDirective();
                    };
                };
                return CriteriaValueInputDirective;
            }());
            angular
                .module('app.processos.pesquisa-avancada')
                .directive('criteriaValueInput', CriteriaValueInputDirective.factory());
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=criteria-value-input.directive.js.map
