/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PeticionamentoAdvogadoController', function ($scope, $state, messages) {

		$scope.$parent.child = $scope;
		$scope.actionId = "registrar-peticao-eletronica";
		
		$scope.validar = function(errors) {
			if ($scope.classe.length === 0) {
				errors += 'Você precisa selecionar <b>a classe processual sugerida</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			$scope.recursos.push($scope.command($scope.classe, $scope.partesPoloAtivo, $scope.partesPoloPassivo, $scope.pecas));
			return true;
		};
	});	
})();