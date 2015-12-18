/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DevolucaoController', function ($log, PeticaoService, $state, $stateParams, messages, properties) {
		var devolucao = this;
		
		devolucao.peticaoId = $stateParams.resources[0];
		
		devolucao.tiposDevolucao = [{id : 'REMESSA_INDEVIDA', nome : "Remessa Indevida"}, {id : 'TRANSITADO', nome : "Transitado"}, {id : 'BAIXADO', nome : "Baixado"}];
		
		devolucao.tipoDevolucao = '';
		
		devolucao.finalizar = function() {
			if (devolucao.tipoDevolucao.length === 0) {
				messages.error('Você precisa selecionar <b>o tipo de devolução</b>.');
				return;
			}
			
			if (!angular.isNumber(devolucao.numeroOficio)) {
				messages.error('Você precisa informar <b>o número do ofício</b>.');
				return;
			}
			
			PeticaoService.devolver(devolucao.peticaoId, new DevolverCommand(devolucao.peticaoId, devolucao.tipoDevolucao, devolucao.numeroOficio)).success(function(data) {
				$state.go('visualizar.peticao', {peticaoId: devolucao.peticaoId});
				messages.success('Petição devolvida com sucesso.');
			});
		};
		
		function DevolverCommand(peticaoId, tipoDevolucao, numeroOficio) {
			var command = {};
			command.peticaoId = peticaoId;
			command.tipoDevolucao = tipoDevolucao; 
			command.numeroOficio = numeroOficio;
			return command;
		}
		
	});

})();

