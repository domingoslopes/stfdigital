/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PeticionamentoOrgaoController', function ($scope, $state, messages, PeticaoService, OrgaoService) {

		$scope.$parent.child = $scope;
		$scope.actionId = "registrar-peticao-eletronica-orgao";
		
		OrgaoService.listarRepresentados().success(function(orgaos) {
			$scope.orgaos = orgaos;
		});
		
		$scope.validar = function(errors) {
			
			if ($scope.classe.length === 0) {
				errors += 'Você precisa selecionar <b>a classe processual sugerida</b>.<br/>';
			}
			
			if (!isFinite(parseInt($scope.orgao))) {
				errors += 'Você precisa selecionar <b>um órgão</b>.<br/>';	
			}
			if (errors) {
				messages.error(errors);
				return false;
			}
			var command = $scope.command($scope.classe, $scope.partesPoloAtivo, $scope.partesPoloPassivo, $scope.pecas);
			command.orgaoId = $scope.orgao;
			$scope.recursos.push(command);
			return true;
		};
	});
})();

