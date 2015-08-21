/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('DistribuicaoController', function (data, $scope, $stateParams, $http, messages, properties, $state) {
		
		$scope.idPeticao = $stateParams.idTarefa;
		
		$scope.ministros = data.data;
		
		$scope.relator = {};
		
		$scope.finalizar = function() {
			$http.post(properties.apiUrl + '/peticao/' + $scope.idPeticao + '/distribuicao', $scope.relator.id).success(function(data) {
				$state.go('dashboard');
				messages.success('<b>' + data.classe + ' #' + data.numero + '</b> distribuída para <b>' + data.relator + '</b>');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pode ser registrada</b> porque ela não está válida.');
				}
			});
		};
	});
	
})();