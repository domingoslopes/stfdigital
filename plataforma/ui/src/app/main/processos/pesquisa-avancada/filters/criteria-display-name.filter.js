var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            /** @ngInject **/
            var CriteriaDisplayNameFilter = (function () {
                function CriteriaDisplayNameFilter() {
                }
                CriteriaDisplayNameFilter.getFilter = function ($filter, $translate) {
                    return function (criteria) {
                        var date = $filter('date');
                        var name = angular.copy(criteria.value);
                        if (criteria.trait.dataType === 'date') {
                            if (_.isArray(name)) {
                                name[0] = date(name[0], 'dd/MM/yyyy');
                                name[1] = date(name[1], 'dd/MM/yyyy');
                            }
                            else {
                                name = date(name, 'dd/MM/yyyy');
                            }
                        }
                        switch (criteria.comparisonOperator) {
                            case pesquisaAvancada.ComparisionOperator.ENTRE:
                                name = (name[0] || '') + ' < ' + criteria.trait.name + ' < ' + (name[1] || '');
                                break;
                            case pesquisaAvancada.ComparisionOperator.MAIORQUE:
                                name = criteria.trait.name + ' > ' + name;
                                break;
                            case pesquisaAvancada.ComparisionOperator.MENORQUE:
                                name = criteria.trait.name + ' < ' + name;
                                break;
                            case pesquisaAvancada.ComparisionOperator.EXISTE:
                                name = $translate.instant('PROCESSOS.PESQUISA-AVANCADA.OPERADOR-COMPARACAO.EXISTE');
                        }
                        return name;
                    };
                };
                CriteriaDisplayNameFilter.filter = function () {
                    /** @ngInject **/
                    var filter = function ($filter, $translate) {
                        return CriteriaDisplayNameFilter.getFilter($filter, $translate);
                    };
                    filter.$inject = ["$filter", "$translate"];
                    return filter;
                };
                return CriteriaDisplayNameFilter;
            }());
            angular
                .module('app.processos.pesquisa-avancada')
                .filter('criteriaDisplayName', CriteriaDisplayNameFilter.filter());
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=criteria-display-name.filter.js.map
