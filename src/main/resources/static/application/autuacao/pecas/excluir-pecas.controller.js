/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.autuacao.controller('ExcluiPecasController', function($scope, $stateParams, messages) {
		
		var resources = [];
		
		var pecasId = [];
		
		resources = $stateParams.resources;
		
		angular.forEach(resources, function(resource){
			pecasId.push(resource.peca.sequencial);
		});
		
		$scope.commands = [{
			processoId : resources[0].processoId, 
			pecas : pecasId
		}];
		
		$scope.validar = function() {
			messages.success('<b> Peça(s) excluída(s) com sucesso </b>');
			return true;
		};
		
	});
	
})();

