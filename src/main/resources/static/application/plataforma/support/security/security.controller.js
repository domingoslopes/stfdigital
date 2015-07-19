/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */ 
(function() {
	'use strict';

	angular.module('plataforma').controller('SecurityController', function ($state, $scope, $log, $window, SecurityService) {
		
		$scope.papeis = SecurityService.papeis();
		
		$scope.ativar = function(papel) {
			$window.sessionStorage.setItem('papel', JSON.stringify(papel));
			$scope.papelAtivo = papel;
			$state.go('root.home', {}, {reload: true});
		}
		
		$scope.ativar($scope.papeis[1]);
		
	});
	
})();

