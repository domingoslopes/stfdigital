/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('MinhasPeticoesDashletController', ['$scope', 'PeticaoService', function($scope, PeticaoService) {
		$scope.titulo = 'Minhas Petições';
		
		PeticaoService.listar().success(function(peticoes) {
			$scope.peticoes = peticoes;
		});
	}]);

})();

