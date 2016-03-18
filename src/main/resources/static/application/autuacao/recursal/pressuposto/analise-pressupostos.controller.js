/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AnalisePressupostoController', function ($stateParams, messages, properties, ProcessoService) {
		
		var analise = this;
		var resource = $stateParams.resources[0];
		analise.obsMotivo = '';
		analise.obsAnalise = '';
		analise.apto = true;
		analise.motivos = [];
		analise.motivoId = '';
		analise.recursos = [];
		
		if (angular.isObject(resource)) {
			if (angular.isDefined(resource.peticaoId)) {
				analise.id = resource.peticaoId;
			} else if (angular.isDefined(resource.processoId)) {
				analise.id = resource.processoId;
			}
		} else {
			analise.id = resource;
		}
		
		ProcessoService.consultarMotivos().success(function(motivos){
			analise.motivos = motivos;
		});
		
		var consultarProcesso = null;
		analise.tarefa = $stateParams.task;
		
		if (analise.tarefa.metadado.tipoInformacao == 'ProcessoRecursal') {
			consultarProcesso = ProcessoService.consultar(analise.id);
		} else {
			consultarProcesso = ProcessoService.consultarPorPeticao(analise.id);
		}
		consultarProcesso.success(function(processo) {
			analise.processo = processo;
		});
		
		analise.validar = function() {
			var errors = '';
			
			if (analise.obsAnalise.length === 0) {
				errors = 'Você precisa escrever uma descrição da análise</b>.<br/>';
			}
			
			if (analise.apto === false) {
				if (analise.motivoId.length === 0) {
					errors += 'Para processo inapto, você precisa informar o motivo da inaptidão.<br/>';
				} else if (analise.obsMotivo.length === 0) {
					errors += 'Para processo inapto, você precisa informar uma observação para o motivo da inaptidão.<br/>';
				}
			}
			
			if (errors.length > 0) {
				messages.error(errors);
				return false;
			}
			
			analise.recursos.push(new AnalisePressupostosCommand(analise.processo.id, analise.apto, analise.motivoId, analise.obsMotivo, analise.obsAnalise));
			return true;
		}
		
		analise.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + analise.processo.classe + '/' + analise.processo.numero + '</b> analisado com sucesso.');
		};

    	function AnalisePressupostosCommand(processoId, aptidao, motivoId, obsMotivo,  obsAnalise){
    		var dto = {};
    		dto.processoId = processoId;
    		dto.classificacao = '';
    		dto.motivos = {};
    		dto.observacao = obsAnalise;
    		
    		if(aptidao){
    			dto.classificacao = 'apto';
    		}else{
    			dto.classificacao = 'inapto';
    			var motivoIdNumber = parseInt(motivoId);
    			dto.motivos[motivoIdNumber] = obsMotivo;	
    		}
 
    		return dto;
    	}
		
	});

})();

