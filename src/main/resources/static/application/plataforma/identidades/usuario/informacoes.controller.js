
/**
 * 
 */

(function(){
	'use strict';
	
	angular.plataforma.controller('InformacoesController', function($scope, UsuarioService) {
		
		$scope.usuario = {};
		
		UsuarioService.recuperarInformacoes().then(function(usuario) {
			$scope.usuario = usuario;
		});
	});
})();
