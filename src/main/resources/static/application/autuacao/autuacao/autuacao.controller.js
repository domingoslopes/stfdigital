/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('autuacao').controller('AutuacaoController', function ($log, $http, $state, $stateParams) {
		var autuacao = this;
		
		autuacao.idPeticao = $stateParams.idTarefa;
		
		autuacao.classificacao = 1;
		
		autuacao.finalizar = function() {
			$http.post('/api/peticao/' + autuacao.idPeticao + '/autuacao', {classificacao:autuacao.classificacao}).success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('root.home');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

