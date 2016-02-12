/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('RevisaoProcessosInaptosController', function ($scope, $log, $state, $stateParams, messages, properties, ProcessoService, PeticaoService) {
		var revisao = this;
		
		var resource = $stateParams.resources[0];
		
		revisao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		
		revisao.obsMotivo = '';
		
		revisao.obsAnalise = '';
		
		revisao.apto = true;
		
		revisao.motivos = [];
		
		revisao.motivoId = undefined;
		
		revisao.recursos = [];
		
		ProcessoService.consultarMotivos().success(function(motivos){
			revisao.motivos = motivos;
		});
		
		ProcessoService.consultarProcessoPeloIdPeticao(revisao.peticaoId).success(function(data){
			revisao.processo = data;
		});
		
		PeticaoService.consultar(revisao.peticaoId).then(function(data) {
			revisao.peticao = data;
		});
		
		
		revisao.validar = function() {
			var errors = null;
			if (revisao.obsAnalise.length === 0) {
				errors = 'Você precisa escrever uma descrição da análise</b>.<br/>';
			}
			
			if (revisao.apto === false && revisao.obsMotivo.length === 0) {
				errors += 'Para processo inapto, você precisa informar o motivo da inaptidão.<br/>';
			}
			
			if (errors) {
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

