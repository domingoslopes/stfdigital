module app.tarefas.painelDeFases {
    'use strict';
    import ITranslatePartialLoaderProvider = angular.translate.ITranslatePartialLoaderProvider;
    import IStateProvider = angular.ui.IStateProvider;

    /** @ngInject * */
    function config($translatePartialLoaderProvider: ITranslatePartialLoaderProvider,
                    $stateProvider: IStateProvider,
                    msNavigationServiceProvider: any) {

        $translatePartialLoaderProvider.addPart('app/main/tarefas/painel-de-fases');

        var boardList = [{
            id : 'processo-de-autuacao',
            name : 'Processo De Autuacao'
        }, {
            id : 'recebimento-de-remessas',
            name : 'Recebimento De Remessas'
        }, {
            id : 'distribuicao-de-processos',
            name : 'Distribuicao De Processos'
        }];

        $stateProvider.state('app.tarefas.painel-de-fases', {
            url : '/painel-de-fases',
            views : {
                'content@app.autenticado' : {
                    templateUrl : 'app/main/tarefas/painel-de-fases/painel-de-fases.html',
                    controller : PainelDeFasesController,
                    controllerAs : 'vm'
                }
            },
            resolve : {
                boardList : () => {
                    return boardList;
                }
            }
        });

        msNavigationServiceProvider.saveItem('tarefas.painel-de-fases', {
            title : 'Painel de Fases',
            icon : 'icon-view-week',
            state : 'app.tarefas.painel-de-fases',
            translation : 'TAREFAS.PAINEL-DE-FASES.PAINEL-DE-FASES',
            weight : 1
        });

        boardList.forEach(board => {
            $stateProvider.state('app.tarefas.painel-de-fases.' + board.id, {
                url : '/' + board.id,
                views : {
                    'content@app.autenticado' : {
                        templateUrl : 'app/main/tarefas/painel-de-fases/painel/painel.html',
                        controller : 'TarefasPainelDeFasesPainelController',
                        controllerAs : 'vm'
                    }
                },
                resolve : {
                    boardData : /** @ngInject **/ BoardService => {
                        return BoardService.getBoardData(board.id);
                    }
                }
            });

            msNavigationServiceProvider.saveItem('tarefas.painel-de-fases.' + board.id, {
                title : board.name,
                icon : 'icon-trello',
                state : 'app.tarefas.painel-de-fases.' + board.id,
                translation : 'TAREFAS.PAINEL-DE-FASES.' + board.id.toUpperCase(),
                weight : 1
            });
        });
    }

    angular
        .module('app.tarefas.painel-de-fases', [ 'app.tarefas', 'classy' ])
        .config(config);
}