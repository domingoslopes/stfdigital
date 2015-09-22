/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DevolucaoController', function ($log, PeticaoService, $state, $stateParams, properties) {
		var devolucao = this;
		
		devolucao.idPeticao = $stateParams.idTarefa;
		
		var command = new DevolverCommand(devolucao.idPeticao);
		
		devolucao.finalizar = function() {
			PeticaoService.devolver(command).success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
		
		function DevolverCommand(peticaoId) {
			vat dto = {};
			dto.peticaoId = peticaoId;
			return dto;
		}
		
	});

})();

