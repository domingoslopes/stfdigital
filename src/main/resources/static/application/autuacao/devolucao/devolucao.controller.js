/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DevolucaoController', function (PeticaoService, $state, $stateParams, messages) {
		var devolucao = this;
		
		var resource = $stateParams.resources[0];
		devolucao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		devolucao.recursos = [];
		devolucao.tiposDevolucao = [{id : 'REMESSA_INDEVIDA', nome : "Remessa Indevida"}, {id : 'TRANSITADO', nome : "Transitado"}, {id : 'BAIXADO', nome : "Baixado"}];
		devolucao.tipoDevolucao = '';
		
		PeticaoService.consultarProcessoWorkflow(devolucao.peticaoId).then(function(data) {
			devolucao.processoWorkflowId = data;
		});
		
		devolucao.validar = function() {
			var errors = null;
			if (devolucao.tipoDevolucao.length === 0) {
				errors = 'Você precisa selecionar <b>o tipo de devolução</b>.<br/>';
			}
			
			if (!angular.isNumber(devolucao.numeroOficio)) {
				errors += 'Você precisa informar <b>o número do ofício</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			devolucao.recursos.push(new DevolverCommand(devolucao.peticaoId, devolucao.tipoDevolucao, devolucao.numeroOficio));
			return true;
		};
		
		devolucao.completar = function() {
			$state.go('visualizar.peticao', {peticaoId: devolucao.peticaoId});
			messages.success('Petição devolvida com sucesso.');
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
