var app;
(function (app) {
    var processos;
    (function (processos) {
        var pesquisaAvancada;
        (function (pesquisaAvancada) {
            'use strict';
            /** @ngInject **/
            config.$inject = ["$translatePartialLoaderProvider", "$stateProvider", "msNavigationServiceProvider"];
            function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
                $translatePartialLoaderProvider.addPart('app/main/processos/pesquisa-avancada');
                $stateProvider.state('app.processos.pesquisa-avancada', {
                    url: '/pesquisa-avancada',
                    views: {
                        'content@app.autenticado': {
                            templateUrl: 'app/main/processos/pesquisa-avancada/pesquisa-avancada.html',
                            controller: 'app.processos.pesquisa-avancada.PesquisaAvancadaController',
                            controllerAs: 'vm'
                        }
                    },
                    resolve: {
                        traits: /** @ngInject */ ["$http", function (/** @ngInject */ $http) {
                            return $http.get('app/data/sample/processos/pesquisa-avancada/traits.json')
                                .then(function (response) {
                                return angular.copy(response.data);
                            });
                        }],
                        searchResults: /** @ngInject */ ["$http", function (/** @ngInject */ $http) {
                            return $http.get('http://localhost:8081/api/processos')
                                .then(function (response) {
                                return angular.copy(response.data);
                            });
                        }],
                        savedSearchs: /** @ngInject */ ["$http", function (/** @ngInject */ $http) {
                            return $http.get('app/data/sample/processos/pesquisa-avancada/saved-searchs.json')
                                .then(function (response) {
                                var savedSearchs = angular.copy(response.data);
                                savedSearchs.forEach(function (savedSearch) {
                                    savedSearch.criterias.forEach(function (criteria) {
                                        if (criteria.trait.dataType === 'date') {
                                            criteria.value = _.isArray(criteria) ?
                                                [new Date(criteria.value[0]), new Date(criteria.value[1])]
                                                : new Date(criteria.value);
                                        }
                                    });
                                });
                                return savedSearchs;
                            });
                        }]
                    }
                });
                msNavigationServiceProvider.saveItem('processos.pesquisa-avancada', {
                    title: 'Pesquisa Avançada',
                    icon: 'icon-magnify',
                    state: 'app.processos.pesquisa-avancada',
                    translation: 'PROCESSOS.PESQUISA-AVANCADA.PESQUISA-AVANCADA',
                    weight: 1
                });
            }
            angular
                .module('app.processos.pesquisa-avancada', ['app.processos'])
                .config(config);
        })(pesquisaAvancada = processos.pesquisaAvancada || (processos.pesquisaAvancada = {}));
    })(processos = app.processos || (app.processos = {}));
})(app || (app = {}));

//# sourceMappingURL=pesquisa-avancada.module.js.map
