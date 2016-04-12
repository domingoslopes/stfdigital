module app.processos.pesquisaAvancada {
    'use strict';
    import IDirective = angular.IDirective;
    import IScope = angular.IScope;
    import IAugmentedJQuery = angular.IAugmentedJQuery;
    import IAttributes = angular.IAttributes;

    interface IOperatorColorAttr extends IAttributes {
        operatorColorNegative: string;
        operatorColorAfirmative: string;
    }

    class OperatorColor implements IDirective {

        public restrict: string = 'A';
        public scope: Object = {
            operator: '=operatorColor'
        };
        public link: Function = OperatorColor.fnLink();

        private static defaults: Object = {
            button: {
                afirmativeClass: 'md-accent',
                negativeClass: 'md-warn'
            },
            other: {
                afirmativeClass: 'md-accent-bg',
                negativeClass: 'md-warn-bg'
            }
        };

        constructor() {}

        private static fnLink(): Function {
            return ($scope: IScope, el: IAugmentedJQuery, attr: IOperatorColorAttr) => {
                var key = el[0].tagName.toLowerCase(),
                    config = OperatorColor.defaults[key] || OperatorColor.defaults['other'];

                $scope.$watch('operator', function(value) {
                    var classes: any[] = _.union(_.values(config), [attr.operatorColorNegative, attr.operatorColorAfirmative]);
                    angular.forEach(classes, (cl) => { attr.$removeClass(cl); });
                    attr.$addClass(
                        value === 'NAO' ?
                            (attr.operatorColorNegative || config.negativeClass) :
                            (attr.operatorColorAfirmative || config.afirmativeClass)
                    );
                });
            }
        }

        public static factory(): ng.IDirectiveFactory {
            return () => {
                return new OperatorColor();
            };
        }
    }

    angular
        .module('app.processos.pesquisa-avancada')
        .directive('operatorColor', OperatorColor.factory());
}