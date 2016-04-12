module app.tarefas.minhasTarefas {
    'use strict';
    import IHttpService = angular.IHttpService;
    import IPromise = angular.IPromise;
    
    export interface ITaskTag {
        id?: number,
        name: string,
        label: string,
        color: string
    }
    
    export interface ITaskAttachment {
        name: string,
        size: number
    }
    
    export interface ITask {
        id?: string,
        title: string,
        notes: string,
        startDate: Date,
        startDateTimestamp: number,
        dueDate: Date,
        dueDateTimestamp: number,
        completed: boolean,
        starred: boolean,
        important: boolean,
        deleted: boolean,
        tags: ITaskTag[],
        attachments: ITaskAttachment[],
        selected?: boolean
    }
    
    export class MinhasTarefasService {
        
        private static apiTarefas: string = 'http://localhost:8081/api/tarefas';
        
        /** @ngInject **/
        constructor(private $http: IHttpService) { }
        
        public get(): IPromise<ITask[]> {
            return this.$http.get(MinhasTarefasService.apiTarefas)
                        .then(response => {
                            var tasks: ITask[] = <ITask[]>response.data;
                            angular.forEach(tasks, task => {
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
        }
    }
    
    angular
        .module('app.tarefas.minhas-tarefas')
        .service('app.tarefas.minhas-tarefas.MinhasTarefasService', MinhasTarefasService);
}