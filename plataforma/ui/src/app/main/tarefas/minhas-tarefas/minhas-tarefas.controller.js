var app;
(function (app) {
    var tarefas;
    (function (tarefas) {
        var minhasTarefas;
        (function (minhasTarefas) {
            'use strict';
            var MinhasTarefasController = (function () {
                MinhasTarefasController.$inject = ["$document", "$mdDialog", "$mdSidenav", "$filter", "$scope", "tasks", "tags"];
                function MinhasTarefasController($document, $mdDialog, $mdSidenav, $filter, $scope, tasks, tags) {
                    this.$document = $document;
                    this.$mdDialog = $mdDialog;
                    this.$mdSidenav = $mdSidenav;
                    this.$filter = $filter;
                    this.$scope = $scope;
                    this.tasks = tasks;
                    this.tags = tags;
                    this.colors = ['blue', 'blue-grey', 'orange', 'pink', 'purple'];
                    this.selectedFilter = {
                        filter: 'Start Date',
                        dueDate: false
                    };
                    this.taskFilters = {
                        search: '',
                        tags: [],
                        completed: false,
                        deleted: false,
                        important: false,
                        starred: false
                    };
                    this.showAllTasks = true;
                    this.taskOrder = '';
                    this.taskOrderDescending = false;
                    this.sortableOptions = {
                        handle: '.handle',
                        forceFallback: true,
                        ghostClass: 'todo-item-placeholder',
                        fallbackClass: 'todo-item-ghost',
                        fallbackOnBody: true,
                        sort: true
                    };
                    this.msScrollOptions = {
                        suppressScrollX: true
                    };
                    this.collapsed = false;
                    this.taskFiltersDefaults = angular.copy(this.taskFilters);
                }
                /**
                 * Prevent default
                 *
                 * @param e
                 */
                MinhasTarefasController.prototype.preventDefault = function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                };
                /**
                 * Open new task dialog
                 *
                 * @param ev
                 * @param task
                 */
                MinhasTarefasController.prototype.openTaskDialog = function (ev, task) {
                    this.$mdDialog.show({
                        controller: minhasTarefas.TaskDialogController,
                        controllerAs: 'vm',
                        templateUrl: 'app/main/tarefas/minhas-tarefas/dialogs/task/task-dialog.html',
                        parent: this.$document.find('body'),
                        targetEvent: ev,
                        clickOutsideToClose: true,
                        locals: {
                            task: task,
                            tasks: this.tasks,
                            event: ev
                        }
                    });
                };
                /**
                 * Toggle completed status of the task
                 *
                 * @param task
                 * @param event
                 */
                MinhasTarefasController.prototype.toggleCompleted = function (task, event) {
                    event.stopPropagation();
                    task.completed = !task.completed;
                };
                /**
                 * Toggle sidenav
                 *
                 * @param sidenavId
                 */
                MinhasTarefasController.prototype.toggleSidenav = function (sidenavId) {
                    this.$mdSidenav(sidenavId).toggle();
                };
                /**
                 * Toggles filter with true or false
                 *
                 * @param filter
                 */
                MinhasTarefasController.prototype.toggleFilter = function (filter) {
                    this.taskFilters[filter] = !this.taskFilters[filter];
                    this.checkFilters();
                };
                /**
                 * Toggles filter with true or empty string
                 * @param filter
                 */
                MinhasTarefasController.prototype.toggleFilterWithEmpty = function (filter) {
                    if (this.taskFilters[filter] === '') {
                        this.taskFilters[filter] = true;
                    }
                    else {
                        this.taskFilters[filter] = '';
                    }
                    this.checkFilters();
                };
                /**
                 * Reset filters
                 */
                MinhasTarefasController.prototype.resetFilters = function () {
                    this.showAllTasks = true;
                    this.taskFilters = angular.copy(this.taskFiltersDefaults);
                };
                /**
                 * Check filters and mark showAllthis.Tasks
                 * as true if no filters selected
                 */
                MinhasTarefasController.prototype.checkFilters = function () {
                    this.showAllTasks = !!angular.equals(this.taskFiltersDefaults, this.taskFilters);
                };
                /**
                 * Filter Due Date
                 *
                 * @param item
                 * @returns {boolean}
                 */
                MinhasTarefasController.prototype.filterByDueDate = function () {
                    var _this = this;
                    return function (item) {
                        if (_this.selectedFilter.dueDate !== false) {
                            if (!item.dueDate) {
                                return false;
                            }
                            var now = (new Date()).getTime() / 1000;
                            return (item.dueDateTimestamp <= (now + _this.selectedFilter.dueDate));
                        }
                        return true;
                    };
                };
                /**
                 * Toggles tag filter
                 *
                 * @param tag
                 */
                MinhasTarefasController.prototype.toggleTagFilter = function (tag) {
                    var i = this.taskFilters.tags.indexOf(tag);
                    if (i > -1) {
                        this.taskFilters.tags.splice(i, 1);
                    }
                    else {
                        this.taskFilters.tags.push(tag);
                    }
                    this.checkFilters();
                };
                /**
                 * Returns if tag exists in the tagsFilter
                 *
                 * @param tag
                 * @returns {boolean}
                 */
                MinhasTarefasController.prototype.isTagFilterExists = function (tag) {
                    return this.taskFilters.tags.indexOf(tag) > -1;
                };
                MinhasTarefasController.prototype.processNewTagKeydown = function (event) {
                    if (event.keyCode !== 13) {
                        return;
                    }
                    this.tags.unshift({
                        "id": this.tags.length + 1,
                        "name": this.newTag.toLowerCase().replace(/\s/, '-'),
                        "label": this.newTag,
                        "color": "#BBBBBB"
                    });
                    this.newTag = "";
                };
                MinhasTarefasController.prototype.countTagsWithFilter = function (filter) {
                    return this.$filter('filter')(this.tasks, filter).length;
                };
                MinhasTarefasController.prototype.hasSelectedTasks = function () {
                    for (var i = 0; i < this.tasks.length; i++) {
                        if (this.tasks[i].selected) {
                            return true;
                        }
                    }
                    return false;
                };
                MinhasTarefasController.prototype.selectedCompleteDistribution = function () {
                    var selectedCount = 0, completedCount = 0;
                    for (var i = 0; i < this.tasks.length; i++) {
                        var task = this.tasks[i];
                        if (task.selected) {
                            selectedCount++;
                            if (task.completed) {
                                completedCount++;
                            }
                        }
                    }
                    if (completedCount === 0) {
                        return 0;
                    }
                    else if (completedCount === selectedCount) {
                        return 1;
                    }
                    else {
                        return 0.5;
                    }
                };
                MinhasTarefasController.prototype.selectedToggleCompleted = function ($event) {
                    for (var i = 0; i < this.tasks.length; i++) {
                        var task = this.tasks[i];
                        if (task.selected) {
                            this.toggleCompleted(task, $event);
                            task.selected = false;
                        }
                    }
                };
                MinhasTarefasController.prototype.removeSelection = function () {
                    for (var i = 0; i < this.tasks.length; i++) {
                        this.tasks[i].selected = false;
                    }
                };
                return MinhasTarefasController;
            }());
            minhasTarefas.MinhasTarefasController = MinhasTarefasController;
            angular
                .module('app.tarefas.minhas-tarefas')
                .controller('app.tarefas.minhas-tarefas.MinhasTarefasController', MinhasTarefasController);
        })(minhasTarefas = tarefas.minhasTarefas || (tarefas.minhasTarefas = {}));
    })(tarefas = app.tarefas || (app.tarefas = {}));
})(app || (app = {}));

//# sourceMappingURL=minhas-tarefas.controller.js.map
