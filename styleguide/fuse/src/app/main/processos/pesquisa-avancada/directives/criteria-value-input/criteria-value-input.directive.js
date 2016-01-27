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
                $scope.$watch('criteria.comparisonOperator', function(op) {
                    var criteria = $scope.criteria,
                        value = criteria.value;
                        
                    if (op == 'ENTRE') {
                        criteria.value = _.isArray(value) ? value : [value];
                    } else {
                        criteria.value = _.isArray(value) ? value[0] : value;
                    }
                });
            }
        };
    });

})();