(function () {
    'use strict';

    var app = angular.module('app.tarefas.minhas-tarefas');
    app.classy.controller({
        name: 'TaskDialogController',

        inject: ['$mdDialog', 'Task', 'Tasks', 'event', '$filter', '$http', '$state', 'properties'],

        init: function() {
            this.translate = this.$filter('translate');

            // Data
            this.task = angular.copy(this.Task);
            this.tasks = this.Tasks;
            this.newTask = false;

            if ( !this.task ) {
                this.task = {
                    'id'                : '',
                    'title'             : '',
                    'notes'             : '',
                    'startDate'         : new Date(),
                    'startDateTimeStamp': new Date().getTime(),
                    'dueDate'           : '',
                    'dueDateTimeStamp'  : '',
                    'completed'         : false,
                    'starred'           : false,
                    'important'         : false,
                    'deleted'           : false,
                    'tags'              : []
                };
                this.newTask = true;
                this.task.tags = [];
            }
        },

        methods: {
            /**
             * Add new task
             */
            addNewTask: function() {
                this.tasks.unshift(this.task);
                closeDialog();
            },

            /**
             * Save task
             */
            saveTask: function() {
                for ( var i = 0; i < this.tasks.length; i++ ) {
                    if ( this.tasks[i].id === this.task.id ) {
                        this.tasks[i] = angular.copy(this.task);
                        break;
                    }
                }

                var close = this.closeDialog;

                if (this.task.title === 'Autuar Processo Originário') {
                    this.$http.post(this.properties.url + ":" +  this.properties.port + "/autuacao/api/processos/autuacao", "{\"processoId\":" + this.task.id.substring(3) + ", \"classeId\":\"ADI\"}").success(function() {
                        close();
                    });
                }

                if (this.task.title === 'Distribuir Processo') {
                    this.$http.post(this.properties.url + ":" +  this.properties.port + "/distribuicao/api/distribuicao", "{\"distribuicaoId\":" + this.task.id.substring(3) + "}").success(function() {
                        close();
                    });
                }

                this.$state.go('app.tarefas.minhas-tarefas', this.$state.params, { reload: true });
            },

            /**
             * Delete task
             */
            deleteTask: function() {
                var confirm = this.$mdDialog.confirm()
                    .title(this.translate('TAREFAS.MINHAS-TAREFAS.DIALOGO.VOCE-TEM-CERTEZA'))
                    .content(this.translate('TAREFAS.MINHAS-TAREFAS.DIALOGO.ESSA-TAREFA-SERA-DELETADA'))
                    .ariaLabel(this.translate('TAREFAS.MINHAS-TAREFAS.DIALOGO.DELETAR-TAREFA'))
                    .ok(this.translate('TAREFAS.MINHAS-TAREFAS.DIALOGO.DELETAR'))
                    .cancel(this.translate('TAREFAS.MINHAS-TAREFAS.DIALOGO.CANCELAR'))
                    .targetEvent(this.event);

                this.$mdDialog.show(confirm).then(function () {
                    for ( var i = 0; i < this.tasks.length; i++ ) {
                        if ( this.tasks[i].id === this.task.id ) {
                            this.tasks[i].deleted = true;
                            break;
                        }
                    }
                }.bind(this));
            },


            /**
             * New tag
             *
             * @param chip
             * @returns {{label: *, color: string}}
             */
            newTag: function(chip) {
                var tagColors = ['#388E3C', '#F44336', '#FF9800', '#0091EA', '#9C27B0'];

                return {
                    name : chip,
                    label: chip,
                    color: tagColors[Math.floor(Math.random() * (tagColors.length))]
                };
            },

            /**
             * Close dialog
             */
            closeDialog: function() {
                this.$mdDialog.hide();
            },

            deleteAttachment: function(attachment, task) {
                var index = task.attachments.indexOf(attachment);

                if (index > -1) {
                    task.attachments.splice(index, 1);
                }
            }

        }
    });
})();