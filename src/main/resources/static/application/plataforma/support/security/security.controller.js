/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('SecurityController', function ($state, $scope, $log, $window, SecurityService) {
		
		var selecionarPapel = function(papel) {
			$window.sessionStorage.setItem('papel', JSON.stringify(papel));
			$scope.papelAtivo = papel;
			$state.go('dashboard', {}, {reload: true});
		}
		
		$scope.papeis = SecurityService.papeis();
		
		$scope.ativar = function(papel) {
			selecionarPapel(papel);
			$window.location.href = '/';
		};
		
		var papel = JSON.parse($window.sessionStorage.getItem('papel'));
		
		if (papel === null) {
			selecionarPapel($scope.papeis[0]);
		} else {
			selecionarPapel(papel);
		}	
	});
	
})();

