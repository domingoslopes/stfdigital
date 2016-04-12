module app.tarefas.minhasTarefas {
    'use strict';

    import IDialogService = angular.material.IDialogService;
    import IFilterService = angular.IFilterService;
    import IHttpService = angular.IHttpService;
    import IStateService = angular.ui.IStateService;
    import ITranslateService = angular.translate.ITranslateService;
    
    export class TaskDialogController {

        public newTask: boolean = false;
        private task: ITask;
        private static apiAutuacao: string = "/autuacao/api/processos/autuacao";
        private static apiDistribuicao: string = "/distribuicao/api/distribuicao";
        
        /** @ngInject **/
        constructor(private $mdDialog: IDialogService,
                    private $translate: ITranslateService, 
                    private $http: IHttpService,
                    private $state: IStateService,
                    task: ITask,
                    private tasks: ITask[],
                    private event: MouseEvent,
                    private properties) {

            this.task = angular.copy(task);

            if (! this.task) {
                this.task = <ITask>{
                    id                : '',
                    title             : '',
                    notes             : '',
                    startDate         : new Date(),
                    startDateTimestamp: new Date().getTime(),
                    dueDate           : null,
                    dueDateTimestamp  : null,
                    completed         : false,
                    starred           : false,
                    important         : false,
                    deleted           : false,
                    tags              : [],
                    attachments       : []
                };
                this.newTask = true;
            }
        }

        /**
         * Add new task
         */
        public addNewTask(): void {
            this.tasks.unshift(this.task);
            this.closeDialog();
        }

        /**
         * Save task
         */
        public saveTask(): void {
            for ( var i = 0; i < this.tasks.length; i++ ) {
                if ( this.tasks[i].id === this.task.id ) {
                    this.tasks[i] = angular.copy(this.task);
                    break;
                }
            }

            if (this.task.title === 'Autuar Processo Originário') {
            	var data: string = "{\"processoId\":" + this.task.id.substring(3) + ", \"classeId\":\"ADI\"}";
                this.$http.post(this.properties.url + ":" +  this.properties.port + TaskDialogController.apiAutuacao, data)
                	.success(() => {
                    	this.closeDialog();
                	});
            }

            if (this.task.title === 'Distribuir Processo') {
            	var data: string = "{\"distribuicaoId\":" + this.task.id.substring(3) + "}";
                this.$http.post(this.properties.url + ":" +  this.properties.port + TaskDialogController.apiDistribuicao, data)
                	.success(() => {
                		this.closeDialog();
                	});
            }
            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
        }

        /**
         * Delete task
         */
        public deleteTask(): void {
            var confirm = this.$mdDialog.confirm()
                .title(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.VOCE-TEM-CERTEZA'))
                .textContent(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.ESSA-TAREFA-SERA-DELETADA'))
                .ariaLabel(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.DELETAR-TAREFA'))
                .ok(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.DELETAR'))
                .cancel(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.CANCELAR'))
                .targetEvent(this.event);

            this.$mdDialog.show(confirm).then(() => {
                for ( var i = 0; i < this.tasks.length; i++ ) {
                    if ( this.tasks[i].id === this.task.id ) {
                        this.tasks[i].deleted = true;
                        break;
                    }
                }
            });
        }

        /**
         * New tag
         *
         * @param chip
         * @returns {{label: *, color: string}}
         */
        public newTag(chip): ITaskTag {
            var tagColors = ['#388E3C', '#F44336', '#FF9800', '#0091EA', '#9C27B0'];

            return <ITaskTag> {
                name : chip,
                label: chip,
                color: tagColors[Math.floor(Math.random() * (tagColors.length))]
            };
        }

        /**
         * Close dialog
         */
        public closeDialog(): void {
            this.$mdDialog.hide();
        }

        /**
         * Delete task attachment
         *
         * @param attachment
         * @param task
         */
        public deleteAttachment(attachment: ITaskAttachment, task: ITask): void {
            var index = task.attachments.indexOf(attachment);
            if (index > -1) {
                task.attachments.splice(index, 1);
            }
        }
    }
    
    angular
        .module('app.tarefas.minhas-tarefas')
        .controller('app.tarefas.minhas-tarefas.TaskDialogController', TaskDialogController);
}