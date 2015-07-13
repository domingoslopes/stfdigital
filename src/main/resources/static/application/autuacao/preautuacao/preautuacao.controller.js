/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('autuacao').controller('PreautuacaoController', function ($log, $http, $state, $stateParams) {
		var preautuacao = this;
		
		preautuacao.idPeticao = $stateParams.idTarefa;
		
		preautuacao.finalizar = function() {
			$http.post('/api/peticao/' + preautuacao.idPeticao + '/preautuacao').success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('root.home');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

