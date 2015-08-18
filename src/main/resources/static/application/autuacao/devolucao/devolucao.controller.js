/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DevolucaoController', function ($log, $http, $state, $stateParams, properties) {
		var devolucao = this;
		
		devolucao.idPeticao = $stateParams.idTarefa;
		
		devolucao.finalizar = function() {
			$http.post(properties.apiUrl + '/peticao/' + devolucao.idPeticao + '/devolucao').success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

