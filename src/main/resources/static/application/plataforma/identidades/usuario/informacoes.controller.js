
/**
 * @author Anderson.Araujo
 */

(function(){
	'use strict';
	
	angular.plataforma.controller('InformacoesController', function($scope, SecurityService, AcessosService) {
		
		$scope.usuario = SecurityService.user();
		$scope.usuario.papeis = [];
		
		AcessosService.papeis($scope.usuario.login).then(function(response) {
			$scope.usuario.papeis = response.data;
		});
	});
})();
