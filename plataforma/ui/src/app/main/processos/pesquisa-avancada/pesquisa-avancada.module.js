(function() {
    'use strict';

    angular.module('app.processos.pesquisa-avancada',
            [ 'app.processos', 'classy' ]).config(config);

    /** @ngInject * */
    function config($translatePartialLoaderProvider, $stateProvider,
            msNavigationServiceProvider) {
        $translatePartialLoaderProvider
                .addPart('app/main/processos/pesquisa-avancada');
        $stateProvider
                .state(
                        'app.processos.pesquisa-avancada',
                        {
                            url : '/pesquisa-avancada',
                            views : {
                                'content@app.autenticado' : {
                                    templateUrl : 'app/main/processos/pesquisa-avancada/pesquisa-avancada.html',
                                    controller : 'ProcessosPesquisaAvancadaController',
                                    controllerAs : 'vm'
                                }
                            },
                            resolve : {
                                traits : /** @ngInject */
                                function($http) {
                                    var traits = {
                                        data : []
                                    };

                                    $http
                                            .get(
                                                    'app/data/sample/processos/pesquisa-avancada/traits.json')
                                            .then(
                                                    function(response) {
                                                        angular.copy(
                                                                response.data,
                                                                traits.data);
                                                    });

                                    return traits;
                                },
                                results : /** @ngInject */
                                function($http) {
                                    var results = {
                                        data : []
                                    };

                                    $http
                                            .get(
                                                    'http://localhost:8081/api/processos')
                                            .then(
                                                    function(response) {
                                                        angular.copy(
                                                                response.data,
                                                                results.data);
                                                    });

                                    return results;
                                },
                                savedSearchs : /** @ngInject */
                                function($http) {
                                    var savedSearchs = {
                                        data : []
                                    };
                                    $http
                                            .get(
                                                    'app/data/sample/processos/pesquisa-avancada/saved-searchs.json')
                                            .then(
                                                    function(response) {
                                                        angular
                                                                .copy(
                                                                        response.data,
                                                                        savedSearchs.data);

                                                        savedSearchs.data
                                                                .forEach(function(
                                                                        savedSearch) {
                                                                    savedSearch.criterias
                                                                            .forEach(function(
                                                                                    criteria) {
                                                                                if (criteria.trait.dataType === 'date') {
                                                                                    criteria.value = _
                                                                                            .isArray(criteria) ? [
                                                                                            new Date(
                                                                                                    criteria.value[0]),
                                                                                            new Date(
                                                                                                    criteria.value[1]) ]
                                                                                            : new Date(
                                                                                                    criteria.value);
                                                                                }
                                                                            });
                                                                });
                                                    });

                                    return savedSearchs;
                                }
                            }
                        });

        msNavigationServiceProvider.saveItem('processos.pesquisa-avancada', {
            title : 'Pesquisa Avançada',
            icon : 'icon-magnify',
            state : 'app.processos.pesquisa-avancada',
            translation : 'PROCESSOS.PESQUISA-AVANCADA.PESQUISA-AVANCADA',
            weight : 1
        });
    }
})();