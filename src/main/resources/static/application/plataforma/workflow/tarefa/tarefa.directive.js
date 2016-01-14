/**
 * Diretiva que cria os botões para assumir ou delegar uma tarefa
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	/**
	 * Botão de uma ação específica
	 * Ex. de uso: 
	 * <action id="excluir_recurso" resources="recursos"
	 * 	btn-class="btn-success"	icon-class="fa fa-hand-peace-o"
	 * 	show-description="false" show-not-allowed="false" /> 
	 */
	angular.plataforma.directive('tarefaActions', ['$q', 'TarefaService', function ($q, TarefaService) {
		return {
			require: '^action',
			restrict : 'AE',
			scope : {
				processo : '=', //obrigatório, utilizado para pesquisar a tarefa ativa
				tarefa : '=' //opcional, bind para retornar a tarefa ativa
			},
			templateUrl : 'application/plataforma/workflow/tarefa/tarefa.tpl.html',
			controller : function($scope) {
				var deferred = $q.defer();
				$scope.tarefas = deferred.promise;
				$scope.tarefa = { responsavel : '', dono : false };
				
				$scope.$watch('processo', function() {
					if (angular.isArray($scope.processo)) {
						if ($scope.processo.length > 0) {
							TarefaService.consultarPorProcessos($scope.processo)
								.then(function(tarefas) {
									var donoTodas = true;
									//Se não for dono de todas não pode realizar as tarefas
									angular.forEach(tarefas, function(tarefa) {
										if (tarefa.dono == false) {
											donoTodas = false;
											return;
										}
									});
									if(donoTodas) {
										$scope.tarefa = tarefas[0];
									}
									deferred.resolve(tarefas);
								}, function() {
									deferred.resolve([]);
								});
						}
					} else if (angular.isDefined($scope.processo)) {
						
						TarefaService.consultarPorProcesso($scope.processo)
							.then(function(tarefa) {
								$scope.tarefa = tarefa;
								deferred.resolve([tarefa]);
							}, function() {
								deferred.resolve([]);
							});
					}
				});
			}
		};
	}]);
})();

