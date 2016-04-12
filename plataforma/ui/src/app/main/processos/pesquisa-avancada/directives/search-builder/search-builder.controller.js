var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            var SearchBuilderController = (function () {
                /** @ngInject **/
                SearchBuilderController.$inject = ["$scope"];
                function SearchBuilderController($scope) {
                    this.logicalOperators = ['E', 'OU', 'NAO'];
                    this.comparisonOperators = {
                        'IGUAL': ['string', 'number', 'currency', 'date', 'list'],
                        'CONTEM': ['string'],
                        'ENTRE': ['number', 'currency', 'date'],
                        'MAIOR-QUE': ['number', 'currency', 'date'],
                        'MENOR-QUE': ['number', 'currency', 'date'],
                        'EXISTE': ['constant', 'string', 'number', 'currency', 'date', 'list']
                    };
                    this.traitSearchText = '';
                    this.newCriteria = {
                        logicalOperator: 'E',
                        trait: null
                    };
                    this.criteriaOrder = '';
                    this.sortableOptions = {
                        ghostClass: 'criteria-item-placeholder',
                        handle: '.handle',
                        forceFallback: true,
                        fallbackClass: 'criteria-item-ghost'
                    };
                    this.traits = $scope.traits;
                    this.search = $scope.search;
                }
                SearchBuilderController.prototype.setCriteriaLogicalOperator = function (criteria, operator) {
                    criteria.logicalOperator = operator;
                };
                SearchBuilderController.prototype.setAsFavorite = function (criteria) {
                    criteria.isFavorite = !criteria.isFavorite;
                };
                SearchBuilderController.prototype.removeCriteria = function (i) {
                    _.pullAt(this.search.criterias, i);
                };
                SearchBuilderController.prototype.querySearch = function (query) {
                    return query ? _.filter(this.traits, this.createFilterFor(query)) : this.traits;
                };
                SearchBuilderController.prototype.addNewCriteria = function () {
                    var trait = this.newCriteria.trait;
                    var criteria = {
                        id: null,
                        logicalOperator: this.newCriteria.logicalOperator,
                        comparisonOperator: trait.dataType === 'constant' ? 'EXISTE' : 'IGUAL',
                        trait: trait,
                        value: trait.dataType === 'constant' ? trait.name : null,
                        isFavorite: false,
                        valid: false
                    };
                    this.traitSearchText = '';
                    this.newCriteria.trait = null;
                    this.search.criterias.push(criteria);
                };
                SearchBuilderController.prototype.getComparisonOperators = function (dataType) {
                    var operatorMapping = this.comparisonOperators;
                    var ops = _.keys(operatorMapping);
                    return _.reject(ops, function (op) {
                        var types = operatorMapping[op];
                        return !_.includes(types, dataType);
                    });
                };
                SearchBuilderController.prototype.createFilterFor = function (query) {
                    var lowercaseQuery = angular.lowercase(query);
                    return function (trait) {
                        if (trait.name.toLowerCase().indexOf(lowercaseQuery) !== -1) {
                            return true;
                        }
                        if (trait.dataType === 'list') {
                            return _.some(trait.values, function (value) {
                                return value.toLowerCase().indexOf(lowercaseQuery) !== -1;
                            });
                        }
                        return false;
                    };
                };
                return SearchBuilderController;
            }());
            pesquisaAvancada.SearchBuilderController = SearchBuilderController;
            angular
                .module('app.processos.pesquisa-avancada')
                .controller('app.processos.pesquisa-avancada.SearchBuilderController', SearchBuilderController);
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=search-builder.controller.js.map
