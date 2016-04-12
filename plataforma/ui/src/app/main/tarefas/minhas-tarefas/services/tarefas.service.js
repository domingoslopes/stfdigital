(function () {
    'use strict';

    angular
        .module('app.tarefas.minhas-tarefas')
        .factory('TarefasService', TarefasService);

    /** @ngInject */
    function TarefasService($q, $http, properties) {
        return {
            get: function() {
                var deferred = $q.defer();

                //$http.get('app/data/sample/tarefas/minhas-tarefas/tarefas.json').then(function (response) {
                $http.get(properties.url + ":" +  properties.port + "/services/api/tarefas").then(function (response) {
                    deferred.resolve(response.data);
                }, function (response) {
                    deferred.reject(response);
                });

                return deferred.promise;
            }
        };
    }
})();