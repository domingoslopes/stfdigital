/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('SecurityController', function ($state, $scope, $window, SecurityService) {
		
		$scope.user = SecurityService.user();
		
		$state.go('root.dashboard');
		
		$scope.logout = function() {
			SecurityService.logout()
				.then(function() {
					$window.location.href = '/login';
				});
		};
	});
	
})();

