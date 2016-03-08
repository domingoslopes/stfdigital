(function(){
	'use strict';

	angular.plataforma.controller('VisualizacaoProcessoController', function ($scope, $log, $state, $stateParams, messages, properties, ProcessoService) {
		
		var processoId = $stateParams.processoId;
		
		$scope.processo = {};
		
		$scope.processoResources = [{'processoId' : processoId}];
		
		ProcessoService.consultar(processoId).success(function(processo) {
			$scope.processo = processo;
		});
		
		$scope.urlConteudo = function(peca) {
			return properties.apiUrl + '/documentos/' + peca.documentoId + "/conteudo";
		};
		
	});
	
})();