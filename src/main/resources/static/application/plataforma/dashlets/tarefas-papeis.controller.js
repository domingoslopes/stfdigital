/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('TarefasPapeisDashletController', ['$scope', 'TarefaService', 'ActionService', '$state', function($scope, TarefaService, ActionService, $state) {
		
		TarefaService.listarPorMeusPapeis().success(function(tarefas) {
			$scope.tarefasDosPapeis = tarefas;
		});
		
		$scope.selecao = [];
		
		$scope.go = function(tarefa) {
			ActionService.get(tarefa.nome)
				.then(function(action) {
					var params = {
							action : action,
							resources : [tarefa.metadado.informacao]
						};
					$state.go(action.id, params);
				});
		};
		
	}]);
	
})();

