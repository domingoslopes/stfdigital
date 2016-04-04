/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('ExcluirPesquisaAvancadaController', function($rootScope, $scope, $state, $stateParams, messages) {
		
		$scope.commands = [{ pesquisaId : $stateParams.resource}];
		
		$scope.sucesso = function() {
			$scope.$parent.modal.close();
			messages.success('<b> Pesquisa excluída com sucesso </b>');
			$rootScope.$broadcast('atualizarMinhasPesquisas');
			$state.go('dashboard');
		};
		
	});
	
})();

