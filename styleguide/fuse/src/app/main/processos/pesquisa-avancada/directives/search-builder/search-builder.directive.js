(function() {
    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.directive('searchBuilder', /** @ngInject */ function() {
        return {
            restrict: 'E',
            scope: {
                traits: '=',
                search: '='
            },
            templateUrl: 'app/main/processos/pesquisa-avancada/directives/search-builder/search-builder.html',
            controller: 'SearchBuilderController',
            controllerAs: 'vm'
        };
    });

})();