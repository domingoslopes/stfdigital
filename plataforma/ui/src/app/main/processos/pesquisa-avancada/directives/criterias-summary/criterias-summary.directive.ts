module app.processos.pesquisaAvancada {
    'use strict';
    import IDirective = angular.IDirective;
    import IDirectiveFactory = angular.IDirectiveFactory;

    class CriteriasSummaryDirective implements IDirective {

        public restrict: string = 'E';
        public scope: Object = {
            criterias: '='
        };
        public templateUrl: string = 'app/main/processos/pesquisa-avancada/directives/criterias-summary/criterias-summary.html';

        public static factory(): IDirectiveFactory {
            return () => {
                return new CriteriasSummaryDirective();
            };
        }
    }

    angular
        .module('app.processos.pesquisa-avancada')
        .directive('criteriasSummary', CriteriasSummaryDirective.factory());
}