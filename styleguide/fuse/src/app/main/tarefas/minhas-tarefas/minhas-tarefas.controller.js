(function () {
    'use strict';

    var app = angular.module('app.tarefas.minhas-tarefas');
        
    app.classy.controller({
        name: 'TarefasMinhasTarefasController',

        inject: ['$document', '$mdDialog', '$mdSidenav', '$filter', 'Tasks', 'Tags', '$scope'],


        init: function() {
            this.tasks = this.Tasks;
            this.tags = this.Tags;
            this.completed = [];
            this.colors = ['blue', 'blue-grey', 'orange', 'pink', 'purple'];
            this.selectedFilter = {
                filter : 'Start Date',
                dueDate: 'Next 3 days'
            };

            // Tasks will be filtered against these models
            this.taskFilters = {
                search   : '',
                tags     : [],
                completed: '',
                deleted  : false,
                important: '',
                starred  : '',
                startDate: '',
                dueDate  : ''
            };
            this.$scope.$watch('vm.taskFilters', function(value) { console.log(value); });
            this.taskFiltersDefaults = angular.copy(this.taskFilters);
            this.showAllTasks = true;

            this.taskOrder = '';
            this.taskOrderDescending = false;

            this.sortableOptions = {
                handle        : '.handle',
                forceFallback : true,
                ghostClass    : 'todo-item-placeholder',
                fallbackClass : 'todo-item-ghost',
                fallbackOnBody: true,
                sort          : true
            };
            this.msScrollOptions = {
                suppressScrollX: true
            };

            angular.forEach(this.tasks, function (task) {
                if ( task.startDate ) {
                    task.startDate = new Date(task.startDate);
                    task.startDateTimestamp = task.startDate.getTime();
                }

                if ( task.dueDate ) {
                    task.dueDate = new Date(task.dueDate);
                    task.dueDateTimestamp = task.dueDate.getTime();
                }
            });
        },

        methods: {

            /**
             * Prevent default
             *
             * @param e
             */
            preventDefault: function(e) {
                e.preventDefault();
                e.stopPropagation();
            },

            /**
             * Open new task dialog
             *
             * @param ev
             * @param task
             */
            openTaskDialog: function(ev, task) {
                this.$mdDialog.show({
                    controller         : 'TaskDialogController',
                    controllerAs       : 'vm',
                    templateUrl        : 'app/main/tarefas/minhas-tarefas/dialogs/task/task-dialog.html',
                    parent             : angular.element(this.$document.body),
                    targetEvent        : ev,
                    clickOutsideToClose: true,
                    locals             : {
                        Task : task,
                        Tasks: this.tasks,
                        event: ev
                    }
                });
            },

            /**
             * Toggle completed status of the task
             *
             * @param task
             * @param event
             */
            toggleCompleted: function(task, event) {
                event.stopPropagation();
                task.completed = !task.completed;
            },

            /**
             * Toggle sidenav
             *
             * @param sidenavId
             */
            toggleSidenav: function(sidenavId) {
                this.$mdSidenav(sidenavId).toggle();
            },

            /**
             * Toggles filter with true or false
             *
             * @param filter
             */
            toggleFilter: function(filter) {
                this.taskFilters[filter] = !this.taskFilters[filter];

                this.checkFilters();
            },

            /**
             * Toggles filter with true or empty string
             * @param filter
             */
            toggleFilterWithEmpty: function(filter) {
                if ( this.taskFilters[filter] === '' ) {
                    this.taskFilters[filter] = true;
                } else {
                    this.taskFilters[filter] = '';
                }

                this.checkFilters();
            },

            /**
             * Reset filters
             */
            resetFilters: function() {
                this.showAllTasks = true;
                this.taskFilters = angular.copy(this.taskFiltersDefaults);
            },

            /**
             * Check filters and mark showAllthis.Tasks
             * as true if no filters selected
             */
            checkFilters: function() {
                this.showAllTasks = !!angular.equals(this.taskFiltersDefaults, this.taskFilters);
            },

            /**
             * Filter by startDate
             *
             * @param item
             * @returns {boolean}
             */
            filterByStartDate: function(item) {
                if ( this.taskFilters.startDate === true ) {
                    return item.startDate === new Date();
                }

                return true;
            },

            /**
             * Filter Due Date
             *
             * @param item
             * @returns {boolean}
             */
            filterByDueDate: function(item) {
                if ( this.taskFilters.dueDate === true ) {
                    return !(item.dueDate === null || item.dueDate.length === 0);
                }

                return true;
            },

            /**
             * Toggles tag filter
             *
             * @param tag
             */
            toggleTagFilter: function(tag) {
                var i = this.taskFilters.tags.indexOf(tag);

                if ( i > -1 ) {
                    this.taskFilters.tags.splice(i, 1);
                } else {
                    this.taskFilters.tags.push(tag);
                }

                this.checkFilters();
            },

            /**
             * Returns if tag exists in the tagsFilter
             *
             * @param tag
             * @returns {boolean}
             */
            isTagFilterExists: function(tag) {
                return this.taskFilters.tags.indexOf(tag) > -1;
            },

            processNewTagKeydown: function(event) {
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
            },

            countTagsWithFilter: function(filter) {
                return this.$filter('filter')(this.tasks, filter).length;
            }
        }
    });
})();