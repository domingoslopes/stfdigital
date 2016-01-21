(function() {
	'use strict';

	angular
		.module('app.tarefas.painel-de-fases', ['app.tarefas', 'classy'])
		.config(config);

	/** @ngInject **/
	function config($translatePartialLoaderProvider, $stateProvider, msNavigationServiceProvider) {
		$translatePartialLoaderProvider.addPart('app/main/tarefas/painel-de-fases');
		
		var BoardList = [
			{
				id: 'processo-de-autuacao',
				name: 'Processo De Autuacao'
			},
			{
				id: 'recebimento-de-remessas',
				name: 'Recebimento De Remessas'
			},
			{
				id: 'distribuicao-de-processos',
				name: 'Distribuicao De Processos'
			}
		];

		$stateProvider
			.state('app.tarefas.painel-de-fases', {
				url: '/painel-de-fases',
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/painel-de-fases/painel-de-fases.html',
						controller: 'PainelDeFasesController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					BoardList: function() {
						return BoardList;
					}
				}
			});

        msNavigationServiceProvider.saveItem('tarefas.painel-de-fases', {
            title      : 'Painel de Fases',
            icon       : 'icon-view-week',
            state      : 'app.tarefas.painel-de-fases',
            translation: 'TAREFAS.PAINEL-DE-FASES.PAINEL-DE-FASES',
            weight     : 1
        });

		BoardList.forEach(function(board) {
			$stateProvider.state('app.tarefas.painel-de-fases.' + board.id, {
				url: '/' + board.id,
				views: {
					'content@app.autenticado': {
						templateUrl: 'app/main/tarefas/painel-de-fases/painel/painel.html',
						controller: 'TarefasPainelDeFasesPainelController',
						controllerAs: 'vm'
					}
				},
				resolve: {
					BoardData: /** @ngInject **/ function(BoardService) {
						return BoardService.getBoardData(board.id);
					}
				}
			});
			
			msNavigationServiceProvider.saveItem('tarefas.painel-de-fases.' + board.id, {
	            title      : board.name,
	            icon       : 'icon-trello',
	            state      : 'app.tarefas.painel-de-fases.' + board.id,
	            translation: 'TAREFAS.PAINEL-DE-FASES.' + board.id.toUpperCase(),
	            weight     : 1
	        });
		});
	}
})();