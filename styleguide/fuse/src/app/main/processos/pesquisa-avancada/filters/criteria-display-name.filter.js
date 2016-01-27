(function() {
    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.filter('criteriaDisplayName', /** @ngInject */ function($filter) {
        var translate = $filter('translate');
        var date = $filter('date');

        return function(criteria) {
            var name = angular.copy(criteria.value);
            
            if (criteria.trait.dataType == 'date') {
                if (_.isArray(name)) {
                    name[0] = date(name[0], 'dd/MM/yyyy');
                    name[1] = date(name[1], 'dd/MM/yyyy');
                } else {
                    name = date(name, 'dd/MM/yyyy');
                }
            }

            switch (criteria.comparisonOperator) {
                case 'ENTRE':
                    name = (name[0] || '') + ' < ' + criteria.trait.name + ' < ' + (name[1] || '');
                    break;

                case 'MAIOR-QUE':
                    name = criteria.trait.name + ' > ' + name;
                    break;

                case 'MENOR-QUE':
                    name = criteria.trait.name + ' < ' + name;
                    break;

                case 'EXISTE':
                    name = translate('PROCESSOS.PESQUISA-AVANCADA.OPERADOR-COMPARACAO.EXISTE');
                    break;
            }

            return name;
        };
    });

})();