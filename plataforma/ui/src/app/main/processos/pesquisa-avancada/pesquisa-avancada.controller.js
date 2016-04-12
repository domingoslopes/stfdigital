var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            var PesquisaAvancadaController = (function () {
                /** @ngInject **/
                PesquisaAvancadaController.$inject = ["$scope", "$translate", "$mdDialog", "$mdToast", "$mdSidenav", "traits", "savedSearchs", "searchResults"];
                function PesquisaAvancadaController($scope, $translate, $mdDialog, $mdToast, $mdSidenav, traits, savedSearchs, searchResults) {
                    this.$scope = $scope;
                    this.$translate = $translate;
                    this.$mdDialog = $mdDialog;
                    this.$mdToast = $mdToast;
                    this.$mdSidenav = $mdSidenav;
                    this.traits = traits;
                    this.savedSearchs = savedSearchs;
                    this.searchResults = searchResults;
                    this.defaultSearch = { id: null, label: '', criterias: [] };
                    this.newSearch = angular.copy(this.defaultSearch);
                    this.loadedSearch = angular.copy(this.defaultSearch);
                    this.resultSearch = angular.copy(this.defaultSearch);
                    this.selectedTab = 0;
                    this.searchComplete = false;
                    this.editEnabled = false;
                    this.resultsDtOptions = this.defineResultsDtOptions();
                }
                PesquisaAvancadaController.prototype.defineResultsDtOptions = function () {
                    return {
                        dom: '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                        pagingType: 'simple',
                        autoWidth: true,
                        responsive: false,
                        searching: false
                    };
                };
                PesquisaAvancadaController.prototype.canSearch = function () {
                    var search = (this.selectedTab === 0 ? this.newSearch : this.loadedSearch);
                    return ((search.criterias.length > 0) && (_.every(search.criterias, 'valid')));
                };
                PesquisaAvancadaController.prototype.doSearch = function () {
                    if (this.selectedTab === 0) {
                        angular.copy(this.newSearch, this.resultSearch);
                        angular.copy(this.newSearch, this.loadedSearch);
                        angular.copy(this.defaultSearch, this.newSearch);
                    }
                    else {
                        angular.copy(this.loadedSearch, this.resultSearch);
                    }
                    this.selectedTab = 1;
                    this.searchComplete = true;
                    this.editEnabled = true;
                };
                PesquisaAvancadaController.prototype.saveSearch = function (event) {
                    this.$mdDialog.show({
                        clickOutsideToClose: true,
                        controller: /** @ngInject */ ["$rootScope", "$mdDialog", "searchName", function ($rootScope, $mdDialog, searchName) {
                            var vm = this;
                            vm.searchName = searchName;
                            vm.cancel = function () {
                                $mdDialog.hide();
                            };
                            vm.confirm = function () {
                                $rootScope.$broadcast('save-search:confirm', vm.searchName);
                                $mdDialog.cancel();
                            };
                        }],
                        resolve: {
                            searchName: function () {
                                return (this.resultSearch.id === null ? '' : this.resultSearch.label);
                            }.bind(this)
                        },
                        controllerAs: 'vm',
                        templateUrl: 'app/main/processos/pesquisa-avancada/modals/save-search-name/save-search-name.html',
                        parent: angular.element(document.body),
                        targetEvent: event
                    });
                    var removeListener = this.$scope.$on('save-search:confirm', function (event, label) {
                        if (this.resultSearch.id === null) {
                            var id = this.savedSearchs.length;
                            this.savedSearchs.push(angular.copy(this.resultSearch));
                            this.savedSearchs[id].id = id;
                            this.savedSearchs[id].label = label;
                        }
                        else {
                            angular.copy(this.resultSearch, this.savedSearchs[this.resultSearch.id]);
                            this.savedSearchs[this.resultSearch.id].label = label;
                        }
                        this.$mdToast.show(this.$mdToast.simple()
                            .textContent(this.$translate("PROCESSOS.PESQUISA-AVANCADA.PESQUISA-SALVA"))
                            .position('top right')
                            .hideDelay(3000));
                        removeListener();
                    });
                };
                PesquisaAvancadaController.prototype.openSavedSearchs = function () {
                    this.$mdSidenav('sidenav').open();
                };
                PesquisaAvancadaController.prototype.loadSearch = function (savedSearch) {
                    angular.copy(savedSearch, this.loadedSearch);
                    this.$mdSidenav('sidenav').close();
                    this.selectedTab = 2;
                    this.editEnabled = true;
                };
                return PesquisaAvancadaController;
            }());
            angular
                .module('app.processos.pesquisa-avancada')
                .controller('app.processos.pesquisa-avancada.PesquisaAvancadaController', PesquisaAvancadaController);
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=pesquisa-avancada.controller.js.map
