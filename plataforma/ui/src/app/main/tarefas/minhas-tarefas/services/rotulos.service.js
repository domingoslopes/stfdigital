var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var minhasTarefas;
        (function (minhasTarefas) {
            'use strict';
            var RotulosService = (function () {
                /** @ngInject **/
                RotulosService.$inject = ["$http"];
                function RotulosService($http) {
                    this.$http = $http;
                }
                RotulosService.prototype.get = function () {
                    return this.$http
                        .get(RotulosService.apiRotulos)
                        .then(function (response) {
                        return response.data;
                    });
                };
                RotulosService.apiRotulos = 'app/data/sample/tarefas/minhas-tarefas/rotulos.json';
                return RotulosService;
            }());
            minhasTarefas.RotulosService = RotulosService;
            angular
                .module('app.tarefas.minhas-tarefas')
                .service('app.tarefas.minhas-tarefas.RotulosService', RotulosService);
        })(minhasTarefas = tarefas.minhasTarefas || (tarefas.minhasTarefas = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=rotulos.service.js.map
