(function() {
    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    var defaults = {
        button: {
            'afirmativeClass': 'md-accent',
            'negativeClass': 'md-warn'
        },
        other: {
            'afirmativeClass': 'md-accent-bg',
            'negativeClass': 'md-warn-bg'
        }
    };

    app.directive('operatorColor', /** @ngInject */ function() {
        return {
            restrict: 'A',
            scope: {
                operator: '=operatorColor'
            },
            link: function ($scope, element, attr) {
                var key = element[0].tagName.toLowerCase(),
                    config = defaults[key] || defaults.other;

                $scope.$watch('operator', function(value) {
                    attr.$removeClass(_.union(_.values(config), [attr.operatorColorNegative, attr.operatorColorAfirmative]));
                    attr.$addClass(
                        value === 'NAO' ? 
                            (attr.operatorColorNegative || config.negativeClass) : 
                            (attr.operatorColorAfirmative || config.afirmativeClass)
                    );
                });
            }
        };
    });

})();