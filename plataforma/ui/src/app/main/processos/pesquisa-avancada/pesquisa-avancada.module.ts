module app.processos.pesquisaAvancada {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject **/
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider) {

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
                traits: /** @ngInject */ $http => {
                    return $http.get('app/data/sample/processos/pesquisa-avancada/traits.json')
                        .then(response => {
                            return angular.copy(response.data);
                        });
                },
                searchResults: /** @ngInject */ $http => {
                    return $http.get('http://localhost:8081/api/processos')
                        .then(response => {
                            return angular.copy(response.data);
                        });
                },
                savedSearchs: /** @ngInject */ $http => {
                    return $http.get('app/data/sample/processos/pesquisa-avancada/saved-searchs.json')
                        .then( response => {
                            var savedSearchs = angular.copy(response.data);
                            savedSearchs.forEach(savedSearch => {
                                savedSearch.criterias.forEach((criteria: any) => {
                                    if (criteria.trait.dataType === 'date') {
                                        criteria.value = _.isArray(criteria) ?
                                            [new Date(criteria.value[0]), new Date(criteria.value[1])]
                                            : new Date(criteria.value);
                                    }
                                });
                            });
                            return savedSearchs;
                        });
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

    angular
        .module('app.processos.pesquisa-avancada', ['app.processos'])
        .config(config);
}