module app.processos.pesquisaAvancada {
    'use strict';
    import IScope = angular.IScope;

    interface SearchBuilderScope extends IScope {
        traits: ITrait[];
        search: ISearch;
    }
    
    export class SearchBuilderController {
        
        public logicalOperators: string[] = ['E', 'OU', 'NAO'];
        public comparisonOperators: Object = {
            'IGUAL': ['string', 'number', 'currency', 'date', 'list'],
            'CONTEM': ['string'],
            'ENTRE': ['number', 'currency', 'date'],
            'MAIOR-QUE': ['number', 'currency', 'date'],
            'MENOR-QUE': ['number', 'currency', 'date'],
            'EXISTE': ['constant', 'string', 'number', 'currency', 'date', 'list']
        };
        public traitSearchText: string = '';
        public newCriteria: ICriteria = <ICriteria> {
            logicalOperator: 'E',
            trait: null
        };
        public traits: ITrait[];
        public search: ISearch;
        public criteriaOrder = '';
        public sortableOptions: Object = {
            ghostClass: 'criteria-item-placeholder',
            handle: '.handle',
            forceFallback: true,
            fallbackClass: 'criteria-item-ghost'
        };
        
        /** @ngInject **/
        constructor($scope: SearchBuilderScope) {
            this.traits = $scope.traits;
            this.search = $scope.search;
        }

        public setCriteriaLogicalOperator(criteria: ICriteria, operator: string): void {
            criteria.logicalOperator = operator;
        }

        public setAsFavorite(criteria: ICriteria): void {
            criteria.isFavorite = !criteria.isFavorite;
        }

        public removeCriteria(i): void {
            _.pullAt(this.search.criterias, i);
        }

        public querySearch(query): ITrait[] {
            return query ? _.filter(this.traits, this.createFilterFor(query)) : this.traits;
        }

        public addNewCriteria(): void {
            var trait: ITrait = this.newCriteria.trait;
            var criteria: ICriteria = <ICriteria> {
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
        }

        public getComparisonOperators(dataType): Object {
            var operatorMapping = this.comparisonOperators;
            var ops = _.keys(operatorMapping);
            return _.reject(ops, function(op) {
                var types = operatorMapping[op];
                return !_.includes(types, dataType);
            });
        }

        private createFilterFor(query): Function {
            var lowercaseQuery = angular.lowercase(query);
            return (trait: ITrait): boolean => {
                if (trait.name.toLowerCase().indexOf(lowercaseQuery) !== -1) {
                    return true;
                }
    
                if (trait.dataType === 'list') {
                    return _.some(trait.values, (value) => {
                        return value.toLowerCase().indexOf(lowercaseQuery) !== -1;
                    });
                }
                return false;
            };
        }
    }

    angular
        .module('app.processos.pesquisa-avancada')
        .controller('app.processos.pesquisa-avancada.SearchBuilderController', SearchBuilderController);
}