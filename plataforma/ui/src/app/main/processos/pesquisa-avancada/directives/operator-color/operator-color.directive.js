var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            var OperatorColor = (function () {
                function OperatorColor() {
                    this.restrict = 'A';
                    this.scope = {
                        operator: '=operatorColor'
                    };
                    this.link = OperatorColor.fnLink();
                }
                OperatorColor.fnLink = function () {
                    return function ($scope, el, attr) {
                        var key = el[0].tagName.toLowerCase(), config = OperatorColor.defaults[key] || OperatorColor.defaults['other'];
                        $scope.$watch('operator', function (value) {
                            var classes = _.union(_.values(config), [attr.operatorColorNegative, attr.operatorColorAfirmative]);
                            angular.forEach(classes, function (cl) { attr.$removeClass(cl); });
                            attr.$addClass(value === 'NAO' ?
                                (attr.operatorColorNegative || config.negativeClass) :
                                (attr.operatorColorAfirmative || config.afirmativeClass));
                        });
                    };
                };
                OperatorColor.factory = function () {
                    return function () {
                        return new OperatorColor();
                    };
                };
                OperatorColor.defaults = {
                    button: {
                        afirmativeClass: 'md-accent',
                        negativeClass: 'md-warn'
                    },
                    other: {
                        afirmativeClass: 'md-accent-bg',
                        negativeClass: 'md-warn-bg'
                    }
                };
                return OperatorColor;
            }());
            angular
                .module('app.processos.pesquisa-avancada')
                .directive('operatorColor', OperatorColor.factory());
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=operator-color.directive.js.map
