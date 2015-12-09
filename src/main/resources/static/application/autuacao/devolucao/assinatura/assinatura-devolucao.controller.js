/**
 * Assinatura do documento de devolução
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 04.12.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AssinaturaDevolucaoController', function($scope, $stateParams, PeticaoService) {
		console.log($stateParams.resources);
		var idsPeticoes = $stateParams.resources;
		
		$scope.peticoes = [];
		
		angular.forEach(idsPeticoes, function(id) {
			PeticaoService.consultar(id).then(function(peticao) {
				$scope.peticoes.push(peticao);
			});
		});
		
	});
	
})();

