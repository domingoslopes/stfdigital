/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.autuacao.controller('UnePecasController', function($scope, $stateParams, messages, PecaService) {
		
		var resources = [];
		
		var documentos = [];
		
		var pecasId = [];
		
		var tamanhoTotalPeca = 0;
		
		resources = $stateParams.resources;
		
		angular.forEach(resources, function(resource){
			PecaService.consultarDocumento(resource.peca.documentoId).then(function(documento){
				documentos.push(documento);
				tamanhoTotalPeca += documento.tamanho;
			});
			pecasId.push(resource.peca.sequencial);
		});
		
		$scope.commands = [{
			processoId : resources[0].processoId, 
			pecas : pecasId
		}];
		
		$scope.validar = function() {
			if (tamanhoTotalPeca > 1000000) {
				messages.error("A união das peças está ultrapassando os 10Mb");
				$scope.modal.close();
				return false;
			}
			messages.success('<b> Peças juntadas com sucesso </b>');
			return true;
		};
		
	});
	
})();

