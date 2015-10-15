/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('SecurityController', function ($state, $scope, $log, $window, SecurityService) {
		
		$scope.papeis = SecurityService.papeis();
		
		$scope.ativar = function(papel) {
			selecionarPapel(papel);
			$window.location.href = '/';
		};
		
		if (angular.isUndefined($window.sessionStorage.getItem('papel'))) {
			selecionarPapel($scope.papeis[0]);
		} else {
			var papel = JSON.parse($window.sessionStorage.getItem('papel'));
			selecionarPapel(papel);
		}
		
		function selecionarPapel(papel) {
			$window.sessionStorage.setItem('papel', JSON.stringify(papel));
			$scope.papelAtivo = papel;
		};
		
	});
	
})();

