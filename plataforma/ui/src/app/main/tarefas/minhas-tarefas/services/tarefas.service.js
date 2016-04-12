var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var minhasTarefas;
        (function (minhasTarefas) {
            'use strict';
            var MinhasTarefasService = (function () {
                /** @ngInject **/
                MinhasTarefasService.$inject = ["$http"];
                function MinhasTarefasService($http) {
                    this.$http = $http;
                }
                MinhasTarefasService.prototype.get = function () {
                    return this.$http.get(MinhasTarefasService.apiTarefas)
                        .then(function (response) {
                        var tasks = response.data;
                        angular.forEach(tasks, function (task) {
                            if (angular.isNumber(task.startDate)) {
                                task.startDate = new Date(task.startDate.valueOf());
                                task.startDateTimestamp = task.startDate.getTime() / 1000;
                            }
                            if (angular.isNumber(task.dueDate)) {
                                task.dueDate = new Date(task.dueDate.valueOf());
                                task.dueDateTimestamp = task.dueDate.getTime() / 1000;
                            }
                        });
                        return tasks;
                    });
                };
                MinhasTarefasService.apiTarefas = 'http://localhost:8081/api/tarefas';
                return MinhasTarefasService;
            }());
            minhasTarefas.MinhasTarefasService = MinhasTarefasService;
            angular
                .module('app.tarefas.minhas-tarefas')
                .service('app.tarefas.minhas-tarefas.MinhasTarefasService', MinhasTarefasService);
        })(minhasTarefas = tarefas.minhasTarefas || (tarefas.minhasTarefas = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=tarefas.service.js.map
