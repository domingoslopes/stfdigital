(function () {

    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.classy.controller({

        name: 'SearchBuilderController',

        inject: ['$mdDialog', '$scope', '$document'],

        init: function() {

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
                operator: 'E',
                trait: null
            };

            this.traits = this.$scope.traits;
            this.search = this.$scope.search;
            
            this.criteriaOrder = '';
            this.sortableOptions = {
                ghostClass: 'criteria-item-placeholder',
                handle: '.handle',
                forceFallback: true,
                fallbackClass: 'criteria-item-ghost'
            };
        },

        methods: {

            setCriteriaLogicalOperator: function(criteria, operator) {
                criteria.logicalOperator = operator;
            },

            setAsFavorite: function(criteria) {
                criteria.isFavorite = !criteria.isFavorite;
            },

            removeCriteria: function(i) {
                _.pullAt(this.search.criterias, i);
            },

            querySearch: function(query) {
                var results = query ? _.filter(this.traits, this._createFilterFor(query)) : this.traits;
                return results;
            },

            addNewCriteria: function() {
                var trait = this.newCriteria.trait;
                var criteria = {
                    id: null,
                    logicalOperator: this.newCriteria.operator,
                    comparisonOperator: trait.dataType === 'constant' ? 'EXISTE' : 'IGUAL',
                    trait: trait,
                    value: trait.dataType === 'constant' ? trait.name : null
                };
                this.traitSearchText = '';
                this.newCriteria.trait = null;
                this.search.criterias.push(criteria);
            },

            getComparisonOperators: function(dataType) {
                var operatorMapping = this.comparisonOperators;
                var ops = _.keys(operatorMapping);
                return _.reject(ops, function(op) {
                    var types = operatorMapping[op];
                    return !_.includes(types, dataType);
                });
            },

            _createFilterFor: function(query) {
                var lowercaseQuery = angular.lowercase(query);
                return function filterFn(trait) {
                    if (trait.name.toLowerCase().indexOf(lowercaseQuery) !== -1) {
                        return true;
                    }

                    if (trait.dataType === 'list') {
                        return _.any(trait.values, function(value) {
                            return value.toLowerCase().indexOf(lowercaseQuery) !== -1;
                        });
                    }

                    return false;
                };
            }

        }

    });

})();