module app.tarefas.minhasTarefas {
    'use strict';

    import IDialogService = angular.material.IDialogService;
    import IFilterService = angular.IFilterService;
    import IHttpService = angular.IHttpService;
    import IStateService = angular.ui.IStateService;
    import ITranslateService = angular.translate.ITranslateService;
    
    export class TaskDialogController {

        private task: ITask;
        public newTask: boolean = false;
        
        /** @ngInject **/
        constructor(private $mdDialog: IDialogService,
                    private $translate: ITranslateService, 
                    private $http: IHttpService,
                    private $state: IStateService,
                    task: ITask,
                    private tasks: ITask[],
                    private event: MouseEvent) {

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
        public addNewTask() {
            this.tasks.unshift(this.task);
            this.closeDialog();
        }

        /**
         * Save task
         */
        public saveTask() {
            for ( var i = 0; i < this.tasks.length; i++ ) {
                if ( this.tasks[i].id === this.task.id ) {
                    this.tasks[i] = angular.copy(this.task);
                    break;
                }
            }
            var close = this.closeDialog;

            if (this.task.title === 'Autuar Processo Originário') {
                this.$http.post("http://localhost:8092/api/processos/autuacao", "{\"processoId\":" + this.task.id.substring(3) + ", \"classeId\":\"ADI\"}").success(function() {
                    close();
                });
            }

            if (this.task.title === 'Distribuir Processo') {
                this.$http.post("http://localhost:8093/api/distribuicao", "{\"distribuicaoId\":" + this.task.id.substring(3) + "}").success(function() {
                    close();
                });
            }
            this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
        }

        /**
         * Delete task
         */
        public deleteTask() {
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
        public closeDialog() {
            this.$mdDialog.hide();
        }

        /**
         * Delete task attachment
         *
         * @param attachment
         * @param task
         */
        public deleteAttachment(attachment: ITaskAttachment, task: ITask) {
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