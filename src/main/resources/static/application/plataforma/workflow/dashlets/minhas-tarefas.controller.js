/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('MinhasTarefasDashletController', ['$scope', 'TarefaService', function($scope, TarefaService) {
		
		TarefaService.listar().success(function(tarefas) {
			$scope.tarefas = tarefas;
			
		});
		
	}]);
	
})();

