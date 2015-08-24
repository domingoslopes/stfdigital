/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('DashboardController', function (data, $scope, $http, properties) {
		
		$scope.tarefas = data.data;
		
		$http.get(properties.apiUrl + '/peticoes').success(function(peticoes) {
			$scope.peticoes = peticoes
		});
		
	});
	
})();

