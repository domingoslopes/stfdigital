/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PeticionamentoAdvogadoController', function ($scope, $state, messages, PeticaoService) {

		$scope.$parent.child = $scope;
		
		$scope.validar = function() {
			if ($scope.classe.length === 0) {
				messages.error('Você precisa selecionar <b>a classe processual sugerida</b>.');
				return false;
			}
			return true;
		};

		$scope.finalizar = function() {
			var command = $scope.command($scope.classe, $scope.partesPoloAtivo, $scope.partesPoloPassivo, $scope.pecas);
			
			PeticaoService.peticionar(command).success(function(data) {
				$state.go('dashboard');
				messages.success('Petição <b>#' + data + '</b> enviada com sucesso.');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pôde ser registrada</b> porque ela não está válida.');
				}
			});
		};
	});	
})();