/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('autuacao').controller('DistribuicaoController', function ($log, $http, $state, $stateParams) {
		var distribuicao = this;
		
		distribuicao.idPeticao = $stateParams.idTarefa;
		
		distribuicao.finalizar = function() {
			$http.post('/api/peticao/' + distribuicao.idPeticao + '/distribuicao').success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

