/**
*
*
**/

(function(){
	'use strict';

	angular.plataforma.controller('VisualizacaoPeticaoController', function ($scope, $log, $state, $stateParams, messages, properties, PeticaoService) {
		
		$scope.peticao = {};
		
		var idPeticao = $stateParams.idPeticao;
		
		PeticaoService.consultar(idPeticao).success(function(data) {
			$scope.peticao = data;
		});
		
		$scope.urlConteudo = function(peca) {
			return properties.apiUrl + '/documentos/' + peca.documentoId;
		};
		
		
	});
	
})();