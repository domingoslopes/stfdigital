/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('SalvarPesquisaAvancadaController', function($scope, $stateParams, messages) {
		
		$scope.nome = ''; 
		$scope.commands = [];
		var pesquisa = $stateParams.resources[0];
		
		var command = {
			nome : $scope.nome,
			consulta : pesquisa.consulta,
			indices : pesquisa.indices
		};
			
		if (angular.isNumber(pesquisa.pesquisaId)) {
			command.pesquisaId = pesquisaId;
		}
		$scope.commands.push(command);
		
		$scope.validar = function() {
			var erros = null;
			
			if ($scope.nome.length == 0) {
				erros = 'Nome de pesquisa inválido!<br/>';
			}
			
			if (pesquisa.consulta.length == 0) {
				erros += 'Consulta inválida!<br/>';
			}
			
			if (pesquisa.indices.length == 0) {
				erros += 'Nenhum índice informado!<br/>';
			}
			
			if (erros) {
				messages.error(erros);
				return false;
			}
			return true;
		};	
	});
	
})();