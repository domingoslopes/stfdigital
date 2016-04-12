module app.processos.pesquisaAvancada {
    'use strict';
    import IScope = angular.IScope;
    import ITranslateService = angular.translate.ITranslateService;
    import IToastService = angular.material.IToastService;
    import ISidenavService = angular.material.ISidenavService;
    import IDialogService = angular.material.IDialogService;
    
    class PesquisaAvancadaController {

        public defaultSearch: ISearch;
        public newSearch: ISearch;
        public loadedSearch: ISearch;
        public resultSearch: ISearch;
        public selectedTab: number;
        public searchComplete: boolean;
        public editEnabled: boolean;
        public resultsDtOptions: any;
        
        /** @ngInject **/
        constructor(private $scope: IScope,
                    private $translate: ITranslateService,
                    private $mdDialog: IDialogService,
                    private $mdToast: IToastService,
                    private $mdSidenav: ISidenavService,
                    public traits: ITrait[],
                    public savedSearchs: ISearch[],
                    public searchResults: any) {

            this.defaultSearch = <ISearch>{id: null, label: '', criterias: []};

            this.newSearch = angular.copy(this.defaultSearch);
            this.loadedSearch = angular.copy(this.defaultSearch);
            this.resultSearch = angular.copy(this.defaultSearch);
            this.selectedTab = 0;
            this.searchComplete = false;
            this.editEnabled = false;
            this.resultsDtOptions = this.defineResultsDtOptions();
        }

        private defineResultsDtOptions(): any {
             return {
                dom: '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                pagingType: 'simple',
                autoWidth: true,
                responsive: false,
                searching: false
            };
        }

        public canSearch(): boolean {
            var search = (this.selectedTab === 0 ? this.newSearch : this.loadedSearch);
            return ((search.criterias.length > 0) && (_.every(search.criterias, 'valid')));
        }

        public doSearch(): void {
            if (this.selectedTab === 0) {
                angular.copy(this.newSearch, this.resultSearch);
                angular.copy(this.newSearch, this.loadedSearch);
                angular.copy(this.defaultSearch, this.newSearch);
            } else {
                angular.copy(this.loadedSearch, this.resultSearch);
            }
            this.selectedTab = 1;
            this.searchComplete = true;
            this.editEnabled = true;
        }

        public saveSearch(event): void {
            this.$mdDialog.show({
                clickOutsideToClose: true,
                controller: /** @ngInject */ function ($rootScope, $mdDialog, searchName) {
                    var vm = this;
                    vm.searchName = searchName;
                    vm.cancel = function () {
                        $mdDialog.hide();
                    };
                    vm.confirm = function () {
                        $rootScope.$broadcast('save-search:confirm', vm.searchName);
                        $mdDialog.cancel();
                    };
                },
                resolve: {
                    searchName: function() {
                        return (this.resultSearch.id === null ? '' : this.resultSearch.label);
                    }.bind(this)
                },
                controllerAs: 'vm',
                templateUrl: 'app/main/processos/pesquisa-avancada/modals/save-search-name/save-search-name.html',
                parent: angular.element(document.body),
                targetEvent: event
            });

            var removeListener = this.$scope.$on('save-search:confirm', function(event, label) {
                if (this.resultSearch.id === null) {
                    var id = this.savedSearchs.length;
                    this.savedSearchs.push(angular.copy(this.resultSearch));
                    this.savedSearchs[id].id = id;
                    this.savedSearchs[id].label = label;
                } else {
                    angular.copy(this.resultSearch, this.savedSearchs[this.resultSearch.id]);
                    this.savedSearchs[this.resultSearch.id].label = label;
                }
                this.$mdToast.show(
                    this.$mdToast.simple()
                        .textContent(this.$translate("PROCESSOS.PESQUISA-AVANCADA.PESQUISA-SALVA"))
                        .position('top right')
                        .hideDelay(3000)
                );
                removeListener();
            });
        }

        public openSavedSearchs(): void {
            this.$mdSidenav('sidenav').open();
        }

        public loadSearch(savedSearch: ISearch): void {
            angular.copy(savedSearch, this.loadedSearch);
            this.$mdSidenav('sidenav').close();
            this.selectedTab = 2;
            this.editEnabled = true;
        }
    }
    
    angular
        .module('app.processos.pesquisa-avancada')
        .controller('app.processos.pesquisa-avancada.PesquisaAvancadaController', PesquisaAvancadaController);
}