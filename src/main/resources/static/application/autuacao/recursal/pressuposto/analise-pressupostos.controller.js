/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AnalisePressupostoController', function ($scope, $log, $state, $stateParams, messages, properties, ProcessoService, PeticaoService) {
		var analise = this;
		
		var resource = $stateParams.resources[0];
		
		analise.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		
		analise.obsMotivo = '';
		
		analise.obsAnalise = '';
		
		analise.apto = true;
		
		analise.motivos = [];
		
		analise.motivoId = '';
		
		analise.recursos = [];
		
		ProcessoService.consultarMotivos().success(function(motivos){
			analise.motivos = motivos;
		});
		
		PeticaoService.consultar(analise.peticaoId).then(function(data) {
			analise.peticao = data;
		});
		
		ProcessoService.consultarProcessoPeloIdPeticao(analise.peticaoId).success(function(data){
			analise.processo = data;
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
			
			analise.recursos.push(new AnalisePressupostosCommand(analise.processo.id, analise.peticaoId, analise.apto, analise.motivoId, analise.obsMotivo, analise.obsAnalise));
			return true;
		}
		
		analise.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + analise.processo.classe + '/' + analise.processo.numero + '</b> analisado com sucesso.');
		};

    	function AnalisePressupostosCommand(processoId, peticaoId, aptidao, motivoId, obsMotivo,  obsAnalise){
    		var dto = {};
    		dto.processoId = processoId;
    		//dto.peticaoId = peticaoId;
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

