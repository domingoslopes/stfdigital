(function () {
    'use strict';

    angular
        .module('app.tarefas.painel-de-fases')
        .factory('BoardService', BoardService);

    /** @ngInject */
    function BoardService($q, $http) {
        var service = { 
            getBoardData: getBoardData,
            data: {}
        };

        /**
         * Get board data from the server
         *
         * @param boardId
         * @returns {*}
         */
        function getBoardData(boardId) {
            var deferred = $q.defer();

            $http.get('app/data/sample/tarefas/painel-de-fases/' + boardId + '.json').then(function (response) {
                service.data = response.data;
                deferred.resolve(response.data);
            }, function (response) {
                deferred.reject(response);
            });

            return deferred.promise;
        }

        return service;
    }
})();