/**
*
*
**/

(function(){
	'use strict';

	angular.plataforma.controller('VisualizacaoPeticaoController', function ($scope, $log, $state, $stateParams, messages, properties, PeticaoService, PecaService) {
		
		$scope.peticao = {};
		
		var idPeticao = $stateParams.idPeticao;
		
		PeticaoService.consultar(idPeticao).then(function(data) {
			$scope.peticao = data;
		});
		
		$scope.urlConteudo = function(peca) {
			return PecaService.montarUrlConteudo(peca);
		};
		
		
	});
	
})();