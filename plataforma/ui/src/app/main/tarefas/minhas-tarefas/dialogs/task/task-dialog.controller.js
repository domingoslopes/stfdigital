var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var minhasTarefas;
        (function (minhasTarefas) {
            'use strict';
            var TaskDialogController = (function () {
                /** @ngInject **/
                TaskDialogController.$inject = ["$mdDialog", "$translate", "$http", "$state", "task", "tasks", "event"];
                function TaskDialogController($mdDialog, $translate, $http, $state, task, tasks, event) {
                    this.$mdDialog = $mdDialog;
                    this.$translate = $translate;
                    this.$http = $http;
                    this.$state = $state;
                    this.tasks = tasks;
                    this.event = event;
                    this.newTask = false;
                    this.task = angular.copy(task);
                    if (!this.task) {
                        this.task = {
                            id: '',
                            title: '',
                            notes: '',
                            startDate: new Date(),
                            startDateTimestamp: new Date().getTime(),
                            dueDate: null,
                            dueDateTimestamp: null,
                            completed: false,
                            starred: false,
                            important: false,
                            deleted: false,
                            tags: [],
                            attachments: []
                        };
                        this.newTask = true;
                    }
                }
                /**
                 * Add new task
                 */
                TaskDialogController.prototype.addNewTask = function () {
                    this.tasks.unshift(this.task);
                    this.closeDialog();
                };
                /**
                 * Save task
                 */
                TaskDialogController.prototype.saveTask = function () {
                    for (var i = 0; i < this.tasks.length; i++) {
                        if (this.tasks[i].id === this.task.id) {
                            this.tasks[i] = angular.copy(this.task);
                            break;
                        }
                    }
                    var close = this.closeDialog;
                    if (this.task.title === 'Autuar Processo Originário') {
                        this.$http.post("http://localhost:8092/api/processos/autuacao", "{\"processoId\":" + this.task.id.substring(3) + ", \"classeId\":\"ADI\"}").success(function () {
                            close();
                        });
                    }
                    if (this.task.title === 'Distribuir Processo') {
                        this.$http.post("http://localhost:8093/api/distribuicao", "{\"distribuicaoId\":" + this.task.id.substring(3) + "}").success(function () {
                            close();
                        });
                    }
                    this.$state.go('app.tarefas.minhas-tarefas', {}, { reload: true });
                };
                /**
                 * Delete task
                 */
                TaskDialogController.prototype.deleteTask = function () {
                    var _this = this;
                    var confirm = this.$mdDialog.confirm()
                        .title(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.VOCE-TEM-CERTEZA'))
                        .textContent(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.ESSA-TAREFA-SERA-DELETADA'))
                        .ariaLabel(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.DELETAR-TAREFA'))
                        .ok(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.DELETAR'))
                        .cancel(this.$translate.instant('TAREFAS.MINHAS-TAREFAS.DIALOGO.CANCELAR'))
                        .targetEvent(this.event);
                    this.$mdDialog.show(confirm).then(function () {
                        for (var i = 0; i < _this.tasks.length; i++) {
                            if (_this.tasks[i].id === _this.task.id) {
                                _this.tasks[i].deleted = true;
                                break;
                            }
                        }
                    });
                };
                /**
                 * New tag
                 *
                 * @param chip
                 * @returns {{label: *, color: string}}
                 */
                TaskDialogController.prototype.newTag = function (chip) {
                    var tagColors = ['#388E3C', '#F44336', '#FF9800', '#0091EA', '#9C27B0'];
                    return {
                        name: chip,
                        label: chip,
                        color: tagColors[Math.floor(Math.random() * (tagColors.length))]
                    };
                };
                /**
                 * Close dialog
                 */
                TaskDialogController.prototype.closeDialog = function () {
                    this.$mdDialog.hide();
                };
                /**
                 * Delete task attachment
                 *
                 * @param attachment
                 * @param task
                 */
                TaskDialogController.prototype.deleteAttachment = function (attachment, task) {
                    var index = task.attachments.indexOf(attachment);
                    if (index > -1) {
                        task.attachments.splice(index, 1);
                    }
                };
                return TaskDialogController;
            }());
            minhasTarefas.TaskDialogController = TaskDialogController;
            angular
                .module('app.tarefas.minhas-tarefas')
                .controller('app.tarefas.minhas-tarefas.TaskDialogController', TaskDialogController);
        })(minhasTarefas = tarefas.minhasTarefas || (tarefas.minhasTarefas = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=task-dialog.controller.js.map
