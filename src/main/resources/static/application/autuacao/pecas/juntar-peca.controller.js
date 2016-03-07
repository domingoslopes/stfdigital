/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.autuacao.controller('JuntaPecasController', function($scope, $stateParams, messages) {
	
		var resource = $stateParams.resources[0];
		
		$scope.commands = [{
			processoId : resource.processoId, 
			pecaId : resource.peca.sequencial
		}];
		
		$scope.validar = function() {
			if (resource.peca.situacao != "PENDENTE_JUNTADA") {
				messages.error("A peça deve estar na situação Pendente de Juntada!");
				$scope.modal.close();
				return false;
			}
			messages.success('<b> Peça juntada com sucesso </b>');
			return true;
		};
		
	});
	
})();

