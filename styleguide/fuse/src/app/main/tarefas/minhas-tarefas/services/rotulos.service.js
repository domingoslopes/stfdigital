(function () {
    'use strict';

    angular
        .module('app.tarefas.minhas-tarefas')
        .factory('RotulosService', RotulosService);

    /** @ngInject */
    function RotulosService($q, $http) {
        return {
            get: function() {
                var deferred = $q.defer();

                $http.get('app/data/sample/tarefas/minhas-tarefas/rotulos.json').then(function (response) {
                    deferred.resolve(response.data);
                }, function (response) {
                    deferred.reject(response);
                });

                return deferred.promise;
            }
        }

        return service;
    }
})();