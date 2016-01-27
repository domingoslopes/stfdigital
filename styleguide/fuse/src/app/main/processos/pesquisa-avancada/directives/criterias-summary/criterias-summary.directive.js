(function() {
    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.directive('criteriasSummary', /** @ngInject */ function() {
        return {
            restrict: 'E',
            scope: {
                criterias: '='
            },
            templateUrl: 'app/main/processos/pesquisa-avancada/directives/criterias-summary/criterias-summary.html'
        };
    });

})();