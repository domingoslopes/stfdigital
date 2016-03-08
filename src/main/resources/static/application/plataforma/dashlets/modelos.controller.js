/**
 * Controller para o dashlet de modelos.
 * 
 * @author Tomas.Godoi
 */
(function() {
	'use strict';
	
	angular.plataforma.controller('ModelosDashletController', function($scope, ModeloService, $location) {
		$scope.modelos = [];
		
		ModeloService.listar().then(function(modelos) {
			$scope.modelos = modelos;
		});
		
		$scope.editar = function(modelo) {
			$location.url('modelo/' + modelo.id + '/editar');
		}
	});
})();