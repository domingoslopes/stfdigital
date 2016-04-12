module app.processos.pesquisaAvancada {
    'use strict';
    import IDirective = angular.IDirective;
    import IDirectiveFactory = angular.IDirectiveFactory;

    export class ComparisionOperator {
        public static IGUAL: string = 'IGUAL';
        public static CONTEM: string = 'CONTEM';
        public static ENTRE: string = 'ENTRE';
        public static MAIORQUE: string = 'MAIOR-QUE';
        public static MENORQUE: string = 'MENOR-QUE';
        public static EXISTE: string = 'EXISTE';
    }

    export interface ISearch {
        id: number,
        label: string,
        criterias: ICriteria[]
    }

    export interface ICriteria {
        id: number;
        value: string | Array<string>;
        trait: ITrait;
        logicalOperator: string;
        comparisonOperator: ComparisionOperator;
        isFavorite: boolean;
        valid: boolean;
    }

    export interface ITrait {
        id: string;
        name: string;
        dataType: string;
        values: string[];
    }
    
    class SearchBuilderDirective implements IDirective {
        
        public restrict: string = 'E';
        public templateUrl: string = 'app/main/processos/pesquisa-avancada/directives/search-builder/search-builder.html';

        public scope: Object = {
            traits: '=',
            search: '='
        };
        public controller: any = SearchBuilderController;
        public controllerAs: string = 'vm';
        
        constructor() { }

        public static factory(): IDirectiveFactory {
            return () => {
                return new SearchBuilderDirective();
            };
        }
        
    }

    angular
        .module('app.processos.pesquisa-avancada')
        .directive('searchBuilder', SearchBuilderDirective.factory())
}