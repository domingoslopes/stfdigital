/**
*
*
**/

(function(){
	'use strict';

	angular.plataforma.controller('VisualizacaoPeticaoController', function ($scope, $log, $state, $stateParams, messages, properties, PeticaoService, DocumentoTemporarioService) {
		
		$scope.peticao = {};
		
		var peticaoId = $stateParams.peticaoId;
		
		$scope.peticaoResources = [{'peticaoId' : peticaoId}];
		
		PeticaoService.consultar(peticaoId).then(function(data) {
			$scope.peticao = data;
		});
		
		$scope.urlConteudo = function(peca) {
			return DocumentoTemporarioService.montarUrlConteudo(peca);
		};
		
		
	});
	
})();