module app.tarefas.minhasTarefas {
    'use strict';
    import IHttpService = angular.IHttpService;
    import IPromise = angular.IPromise;

    export class RotulosService {

        private static apiRotulos = 'app/data/sample/tarefas/minhas-tarefas/rotulos.json';

        /** @ngInject **/
        constructor(private $http: IHttpService) { }

        public get(): IPromise<ITaskTag[]> {
            return this.$http
                        .get(RotulosService.apiRotulos)
                        .then(response => {
                            return response.data;
                        });
        }
    }

    angular
        .module('app.tarefas.minhas-tarefas')
        .service('app.tarefas.minhas-tarefas.RotulosService', RotulosService);
}