/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';

	angular.module('plataforma').controller('DashboardController', function (data, $scope) {
		
		$scope.tarefas = data.data;
	});
	
})();

