/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoController', function ($log, $http, $state, $stateParams, properties) {
		var autuacao = this;
		
		autuacao.idPeticao = $stateParams.idTarefa;
		
		autuacao.peticaoValida = 'true';
		
		autuacao.finalizar = function() {
			$http.post(properties.apiUrl + '/peticao/' + autuacao.idPeticao + '/autuacao', {peticaoValida:autuacao.peticaoValida}).success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

