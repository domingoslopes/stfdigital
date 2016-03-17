/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('ExcluirPesquisaController', function($scope, $stateParams, messages) {
		
		var pesquisaId = [];
		
		pesquisaId = $stateParams.pesquisa;
		
		$scope.commands = [{
			pesquisa : pesquisaId 
		}];
		
		$scope.validar = function() {
			messages.success('<b> Pesquisa excluída com sucesso </b>');
			return true;
		};
		
	});
	
})();

