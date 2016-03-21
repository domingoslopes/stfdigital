/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('RevisaoProcessosInaptosController', function ($state, $stateParams, messages, properties, ProcessoService) {
		
		var revisao = this;
		var resource = $stateParams.resources[0];
		revisao.obsMotivo = '';
		revisao.obsAnalise = '';
		revisao.apto = false;
		revisao.motivos = [];
		revisao.motivoId = '';
		revisao.recursos = [];
		
		if (angular.isObject(resource)) {
			if (angular.isDefined(resource.peticaoId)) {
				revisao.id = resource.peticaoId;
			} else if (angular.isDefined(resource.processoId)) {
				revisao.id = resource.processoId;
			}
		} else {
			revisao.id = resource;
		}
		
		ProcessoService.consultarMotivos().success(function(motivos){
			revisao.motivos = motivos;
		});
		
		var consultarProcesso = null;
		revisao.tarefa = $stateParams.task;
		
		if (revisao.tarefa.metadado.tipoInformacao == 'ProcessoRecursal') {
			consultarProcesso = ProcessoService.consultar(revisao.id);
		} else {
			consultarProcesso = ProcessoService.consultarPorPeticao(revisao.id);
		}
		consultarProcesso.success(function(processo) {
			revisao.processo = processo;
		});
		
		revisao.validar = function() {
			var errors = '';
			
			if (revisao.obsAnalise.length === 0) {
				errors = 'Você precisa escrever uma descrição da análise</b>.<br/>';
			}
			
			if (revisao.apto === false) {
				if (revisao.motivoId.length === 0) {
					errors += 'Para processo inapto, você precisa informar o motivo da inaptidão.<br/>';
				} else if (revisao.obsMotivo.length === 0) {
					errors += 'Para processo inapto, você precisa informar uma observação para o motivo da inaptidão.<br/>';
				}
			}

			if (errors.length > 0) {
				messages.error(errors);
				return false;
			}
			
			revisao.recursos.push(new RevisaoInaptoCommand(revisao.processo.id, revisao.apto, revisao.motivoId, revisao.obsMotivo, revisao.obsAnalise));
			return true;
		}
		
		revisao.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + revisao.processo.classe + '/' + revisao.processo.numero + '</b> revisado com sucesso.');
		};
		

		function RevisaoInaptoCommand(processoId, aptidao, motivoId, obsMotivo,  obsAnalise){
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

