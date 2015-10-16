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
		
		devolucao.idPeticao = $stateParams.idTarefa;
		
		devolucao.tiposDevolucao = [{id : 'REMESSA_INDEVIDA', nome : "Remessa Indevida"}, {id : 'TRANSITADO', nome : "Transitado"}, {id : 'BAIXADO', nome : "Baixado"}];
		
		devolucao.tipoDevolucao = '';
		
		devolucao.numeroOficio;
		
		devolucao.finalizar = function() {
			if (devolucao.tipoDevolucao.length === 0) {
				messages.error('Você precisa selecionar <b>o tipo de devolução</b>.');
				return;
			}
			
			if (!angular.isNumber(devolucao.numeroOficio)) {
				messages.error('Você precisa informar <b>o número do ofício</b>.');
				return;
			}
			
			PeticaoService.devolver(devolucao.idPeticao, new DevolverCommand(devolucao.tipoDevolucao, devolucao.numeroOficio)).success(function(data) {
				$state.go('detalhe', {idPeticao: devolucao.idPeticao});
				messages.success('Petição devolvida com sucesso.');
			});
		};
		
		function DevolverCommand(tipoDevolucao, numeroOficio) {
			var command = {};
			command.tipoDevolucao = tipoDevolucao; 
			command.numeroOficio = numeroOficio;
			return command;
		}
		
	});

})();

