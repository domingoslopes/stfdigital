/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('SalvarPesquisaAvancadaController', function($rootScope, $scope, $stateParams, messages) {
		
		var pesquisa = $stateParams.resources[0];
		
		$scope.modalMessages = {};
		$scope.nome = angular.isDefined(pesquisa.nome) ? pesquisa.nome : ''; 
		$scope.commands = [];
		
		$scope.validar = function() {
			var erro = false;
			
			if ($scope.nome.length == 0) {
				$scope.modalMessages.error('Nome de pesquisa inválido!');
				erro = true;
			}
			
			if (pesquisa.consulta.length == 0) {
				$scope.modalMessages.error('Consulta inválida!');
				erro = true;
			}
			
			if (pesquisa.indices.length == 0) {
				$scope.modalMessages.error('Nenhum índice informado!');
				erro = true;
			}
			
			if (!erro) {
				var command = {
					nome : $scope.nome,
					consulta : JSON.stringify(pesquisa.consulta),
					indices : pesquisa.indices
				};
						
				if (angular.isNumber(pesquisa.pesquisaId)) {
					command.pesquisaId = pesquisa.pesquisaId;
				}
				$scope.commands.push(command);
			}
			return !erro;
		};
		
		$scope.finalizar = function() {
			$scope.$parent.modal.close();
			messages.success('Pesquisa salva com sucesso!');
			$rootScope.$broadcast('atualizarMinhasPesquisas');
		};
	});
	
})();