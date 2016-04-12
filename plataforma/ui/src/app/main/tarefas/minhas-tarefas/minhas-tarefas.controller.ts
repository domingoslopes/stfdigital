module app.tarefas.minhasTarefas {
    'use strict';
    import IDialogService = angular.material.IDialogService;
    import ISidenavService = angular.material.ISidenavService;
    import IFilterService = angular.IFilterService;
    import IScope = angular.IScope;
    import IHttpService = angular.IHttpService;
    import IDocumentService = angular.IDocumentService;

    interface ISelectedFilter {
        filter: string;
        dueDate: number | boolean;
    }

    interface ITaskFilters {
        search   : string,
        tags     : ITaskTag[],
        completed: boolean,
        deleted  : boolean,
        important: boolean,
        starred  : boolean
    }

    export class MinhasTarefasController {
        
        public completed: Array<string>;
        public colors: Array<string> = ['blue', 'blue-grey', 'orange', 'pink', 'purple'];
        public selectedFilter: ISelectedFilter = <ISelectedFilter>{
            filter : 'Start Date',
            dueDate: false
        };
        public taskFilters: ITaskFilters = <ITaskFilters>{
            search   : '',
            tags     : [],
            completed: false,
            deleted  : false,
            important: false,
            starred  : false
        };
        public taskFiltersDefaults: ITaskFilters;
        public showAllTasks: boolean = true;
        public taskOrder: string = '';
        public taskOrderDescending: boolean = false;
        public sortableOptions: Object = {
            handle        : '.handle',
            forceFallback : true,
            ghostClass    : 'todo-item-placeholder',
            fallbackClass : 'todo-item-ghost',
            fallbackOnBody: true,
            sort          : true
        };
        public msScrollOptions: Object = {
            suppressScrollX: true
        };
        public collapsed: boolean = false;
        public newTag: string;
        
        static $inject: ['$document', '$mdDialog', '$mdSidenav', '$filter', '$scope', 'tasks', 'tags'];
        
        constructor(private $document: IDocumentService,
                    private $mdDialog: IDialogService, 
                    private $mdSidenav: ISidenavService, 
                    private $filter: IFilterService, 
                    private $scope: IScope,
                    private tasks: ITask[],
                    private tags: ITaskTag[]) {

            this.taskFiltersDefaults = angular.copy(this.taskFilters);
        }

        /**
         * Prevent default
         *
         * @param e
         */
        public preventDefault(e: Event): void {
            e.preventDefault();
            e.stopPropagation();
        }

        /**
         * Open new task dialog
         *
         * @param ev
         * @param task
         */
        public openTaskDialog(ev: MouseEvent, task: ITask): void {
            this.$mdDialog.show({
                controller         : TaskDialogController,
                controllerAs       : 'vm',
                templateUrl        : 'app/main/tarefas/minhas-tarefas/dialogs/task/task-dialog.html',
                parent             : this.$document.find('body'),
                targetEvent        : ev,
                clickOutsideToClose: true,
                locals             : {
                    task : task,
                    tasks: this.tasks,
                    event: ev
                }
            });
        }

        /**
         * Toggle completed status of the task
         *
         * @param task
         * @param event
         */
        public toggleCompleted(task: ITask, event: Event): void {
            event.stopPropagation();
            task.completed = !task.completed;
        }

        /**
         * Toggle sidenav
         *
         * @param sidenavId
         */
        public toggleSidenav(sidenavId: string): void {
            this.$mdSidenav(sidenavId).toggle();
        }

        /**
         * Toggles filter with true or false
         *
         * @param filter
         */
        public toggleFilter(filter: string): void {
            this.taskFilters[filter] = !this.taskFilters[filter];
            this.checkFilters();
        }

        /**
         * Toggles filter with true or empty string
         * @param filter
         */
        public toggleFilterWithEmpty(filter: string): void {
            if ( this.taskFilters[filter] === '' ) {
                this.taskFilters[filter] = true;
            } else {
                this.taskFilters[filter] = '';
            }
            this.checkFilters();
        }

        /**
         * Reset filters
         */
        public resetFilters(): void {
            this.showAllTasks = true;
            this.taskFilters = angular.copy(this.taskFiltersDefaults);
        }

        /**
         * Check filters and mark showAllthis.Tasks
         * as true if no filters selected
         */
        public checkFilters(): void {
            this.showAllTasks = !!angular.equals(this.taskFiltersDefaults, this.taskFilters);
        }

        /**
         * Filter Due Date
         *
         * @param item
         * @returns {boolean}
         */
        public filterByDueDate(): Function {
            return (item: ITask): boolean => {
                if (this.selectedFilter.dueDate !== false) {
                    if (! item.dueDate) {
                        return false;
                    }
                    var now = (new Date()).getTime() / 1000;
                    return (item.dueDateTimestamp <= (now + <number>this.selectedFilter.dueDate));
                }
                return true;
            };
        }

        /**
         * Toggles tag filter
         *
         * @param tag
         */
        public toggleTagFilter(tag: ITaskTag): void {
            var i = this.taskFilters.tags.indexOf(tag);

            if ( i > -1 ) {
                this.taskFilters.tags.splice(i, 1);
            } else {
                this.taskFilters.tags.push(tag);
            }
            this.checkFilters();
        }

        /**
         * Returns if tag exists in the tagsFilter
         *
         * @param tag
         * @returns {boolean}
         */
        public isTagFilterExists(tag: ITaskTag): boolean {
            return this.taskFilters.tags.indexOf(tag) > -1;
        }

        public processNewTagKeydown(event: KeyboardEvent): void {
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
        }

        public countTagsWithFilter(filter: string): number {
            return this.$filter('filter')(this.tasks, filter).length;
        }

        public hasSelectedTasks(): boolean {
            for (var i = 0; i < this.tasks.length; i++) {
                if (this.tasks[i].selected) {
                    return true;
                }
            }
            return false;
        }

        public selectedCompleteDistribution(): number {
            var selectedCount: number = 0,
                completedCount: number = 0;

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
            } else if (completedCount === selectedCount) {
                return 1;
            } else {
                return 0.5;
            }
        }

        public selectedToggleCompleted($event: Event): void {
            for (var i = 0; i < this.tasks.length; i++) {
                var task = this.tasks[i];
                if (task.selected) {
                    this.toggleCompleted(task, $event);
                    task.selected = false;
                }
            }
        }

        public removeSelection(): void {
            for (var i = 0; i < this.tasks.length; i++) {
                this.tasks[i].selected = false;
            }
        }
    }

    angular
        .module('app.tarefas.minhas-tarefas')
        .controller('app.tarefas.minhas-tarefas.MinhasTarefasController', MinhasTarefasController);
}