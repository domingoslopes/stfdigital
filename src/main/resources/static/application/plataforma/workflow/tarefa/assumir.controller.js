/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('AssumirController', function($scope, $stateParams) {
		
		$scope.commands = [];
		
		var AssumirTarefaCommand = function(id) {
			return { tarefaId : id };
		};
		
		angular.forEach($stateParams.resources, function(tarefa) {
			$scope.commands.push(new AssumirTarefaCommand(tarefa.id));
		});
		
	});
	
})();

