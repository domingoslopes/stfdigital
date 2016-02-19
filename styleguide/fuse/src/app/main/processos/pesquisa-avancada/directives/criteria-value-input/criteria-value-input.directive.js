(function() {

    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.directive('criteriaValueInput', /** @ngInject */ function() {
        return {
            restrict: 'E',
            scope: {
                criteria: '='
            },
            templateUrl: 'app/main/processos/pesquisa-avancada/directives/criteria-value-input/criteria-value-input.html',
            link: function($scope, el, attr) {

                var updateCriteriaValidity = function(criteria) {
                    switch (criteria.comparisonOperator) {
                        case 'IGUAL':
                        case 'CONTEM':
                        case 'MAIOR-QUE':
                        case 'MENOR-QUE':
                            if (criteria.trait.dataType == 'date') {
                                criteria.valid = (!!criteria.value);
                            } else {
                                criteria.valid = ((!!criteria.value) && (criteria.value.length > 0));
                            }
                            break;

                        case 'ENTRE':
                            if (criteria.trait.dataType == 'date') {
                                criteria.valid = ((!!criteria.value[0]) && (!!criteria.value[1]));
                            } else {
                                criteria.valid = ((!!criteria.value[0]) && (!!criteria.value[1]) && (criteria.value[0].length > 0) && (criteria.value[1].length > 0));
                            }

                            break;

                        case 'EXISTE':
                            criteria.valid = true;
                            break;
                    }
                }

                $scope.$watch('criteria.value', function(value) {
                    updateCriteriaValidity($scope.criteria);
                });

                $scope.$watch('criteria.value[0]', function(value) {
                    updateCriteriaValidity($scope.criteria);
                });

                $scope.$watch('criteria.value[1]', function(value) {
                    updateCriteriaValidity($scope.criteria);
                });

                $scope.$watch('criteria.comparisonOperator', function(op) {
                    var criteria = $scope.criteria,
                        value = criteria.value;

                    if (op == 'ENTRE') {
                        criteria.value = _.isArray(value) ? value : [value];
                    } else {
                        criteria.value = _.isArray(value) ? value[0] : value;
                    }

                    updateCriteriaValidity(criteria);
                });
            }
        };
    });

})();