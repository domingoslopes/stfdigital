/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('MinhasTarefasDashletController', ['$scope', 'TarefaService', 'ActionService', '$state', function($scope, TarefaService, ActionService, $state) {
		
		TarefaService.listar().success(function(tarefas) {
			$scope.tarefas = tarefas;
		});
		
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

