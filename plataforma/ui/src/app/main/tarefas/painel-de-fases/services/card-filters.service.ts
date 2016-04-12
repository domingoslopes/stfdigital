(function ()
{
    'use strict';

    angular
        .module('app.tarefas.painel-de-fases')
        .factory('CardFilters', CardFiltersService);

    /** @ngInject */
    function CardFiltersService() {
        var service = {
            name   : '',
            labels : [],
            clear  : clear,
            isOn   : isOn
        };

        /**
         * Clear
         */
        function clear() {
            service.name = '';
            service.labels = [];
        }

        /**
         * Is on
         *
         * @returns {boolean}
         */
        function isOn() {
            return (
                (service.name !== '') || 
                (service.labels.length !== 0)
            );
        }

        return service;
    }
})();