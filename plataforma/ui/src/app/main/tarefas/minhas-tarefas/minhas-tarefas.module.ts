module app.tarefas.minhasTarefas {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject * */
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider: any) {

        $translatePartialLoaderProvider.addPart('app/main/tarefas/minhas-tarefas');

        $stateProvider.state('app.tarefas.minhas-tarefas', {
            url : '/minhas-tarefas',
            views : {
                'content@app.autenticado' : {
                    templateUrl : 'app/main/tarefas/minhas-tarefas/minhas-tarefas.html',
                    controller : MinhasTarefasController,
                    controllerAs : 'vm'
                }
            },
            resolve : {
                tasks : ['app.tarefas.minhas-tarefas.MinhasTarefasService', (minhasTarefasService: MinhasTarefasService) => {
                        return minhasTarefasService.get();
                }],
                tags : ['app.tarefas.minhas-tarefas.RotulosService', (rotulosService: RotulosService) => {
                    return rotulosService.get();
                }]
            }
        });

        msNavigationServiceProvider.saveItem('tarefas.minhas-tarefas', {
            title : 'Minhas Tarefas',
            icon : 'icon-view-list',
            state : 'app.tarefas.minhas-tarefas',
            translation : 'TAREFAS.MEUS-PAINEIS.MEUS-PAINEIS',
            weight : 1,
            badge : {
                content : 1,
                color : '#03A9F3'
            }
        });
    }

    angular
        .module('app.tarefas.minhas-tarefas', ['app.tarefas'])
        .config(config);
}