module app.processos.pesquisaAvancada {
    'use strict';
    import IFilterDate = ng.IFilterDate;
    import ITranslateService = ng.translate.ITranslateService;
    import IFilterService = ng.IFilterService;

    /** @ngInject **/
    class CriteriaDisplayNameFilter {

        private static getFilter($filter: IFilterService,
                              $translate: ITranslateService): Function {

            return (criteria: ICriteria) => {

                var date: IFilterDate = $filter('date');
                var name: string | Array<string> = angular.copy(criteria.value);

                if (criteria.trait.dataType === 'date') {
                    if (_.isArray(name)) {
                        name[0] = date(name[0], 'dd/MM/yyyy');
                        name[1] = date(name[1], 'dd/MM/yyyy');
                    } else {
                        name = date(<string> name, 'dd/MM/yyyy');
                    }
                }

                switch (criteria.comparisonOperator) {
                    case ComparisionOperator.ENTRE :
                        name = (name[0] || '') + ' < ' + criteria.trait.name + ' < ' + (name[1] || '');
                        break;

                    case ComparisionOperator.MAIORQUE:
                        name = criteria.trait.name + ' > ' + name;
                        break;

                    case ComparisionOperator.MENORQUE:
                        name = criteria.trait.name + ' < ' + name;
                        break;

                    case ComparisionOperator.EXISTE:
                        name = $translate.instant('PROCESSOS.PESQUISA-AVANCADA.OPERADOR-COMPARACAO.EXISTE');
                }
                return <string> name;
            };
        }

        public static filter(): Function {
            /** @ngInject **/
            var filter = ($filter, $translate) => {
                return CriteriaDisplayNameFilter.getFilter($filter, $translate);
            }
            return filter;
        }
    }

    angular
        .module('app.processos.pesquisa-avancada')
        .filter('criteriaDisplayName', CriteriaDisplayNameFilter.filter());
}