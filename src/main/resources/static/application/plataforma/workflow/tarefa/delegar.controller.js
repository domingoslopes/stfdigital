/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('DelegarController', function($scope, $stateParams, SecurityService, messages) {
		
		var gestor = SecurityService.user();
		
		$scope.pesquisaPessoa = {
				texto : 'pessoa.nome',
				indices : ['usuario'],
				filtros : ['pessoa.nome', 'login'],
				filtrosFixos: {'lotacao.codigo': [gestor.setorLotacao.codigo] },
				pesquisa : 'sugestao'
			};
		
		$scope.commands = [];
		
		var DelegarTarefaCommand = function(tarefaId, usuarioId) {
			return {
				tarefaId : tarefaId, 
				usuarioId : usuarioId
			};
		};
		
		angular.forEach($stateParams.resources, function(tarefa) {
			$scope.commands.push(new DelegarTarefaCommand(tarefa.id, null));
		});
		
		$scope.validar = function() {
			if (angular.isObject($scope.usuario) ) {
				angular.forEach($scope.commands, function(command) {
					command.usuarioId = $scope.usuario.id;
				});
				return true;
			} else {
				messages.error("Selecione um usuário!");
				return false;
			} 
		};
		
	});
	
})();

