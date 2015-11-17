/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('SecurityController', function ($state, $scope, $log, $window, SecurityService, properties, $rootScope) {
		
		var selecionarPapel = function(papel) {
			$window.sessionStorage.setItem('papel', JSON.stringify(papel));
			$scope.papelAtivo = papel;
			$state.go('dashboard', {}, {reload: true});
		}
		
		$scope.papeis = SecurityService.papeis();
		selecionarPapel($scope.papeis[0]);
		
		$scope.ativar = function(papel) {
			selecionarPapel(papel);
			$window.location.href = '/';
		};
		
		$scope.logout = function() {
			SecurityService.logout()
				.then(function() {
					$window.location.href = '/login';
				});
		};
		
	});
	
})();

