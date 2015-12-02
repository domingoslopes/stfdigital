/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoController', function ($log, $state, $stateParams, messages, properties, ClasseService, PeticaoService) {
		var autuacao = this;
		
		autuacao.idPeticao = $stateParams.idTarefa;
		
		autuacao.classe = '';
		
		autuacao.valida = 'true';
		
		autuacao.motivo = '';
		
		autuacao.classes = [];
		
		autuacao.peticao = {};
		
		ClasseService.listar().success(function(classes) {
			autuacao.classes = classes;
		});
		
		PeticaoService.consultar(autuacao.idPeticao).success(function(data) {
			autuacao.peticao = data;
		});
		
		autuacao.finalizar = function() {
			if (autuacao.classe.length === 0) {
				messages.error('Você precisa selecionar <b>a classe processual definitiva</b>.');
				return;
			}
			
			if (autuacao.valida === 'false' && autuacao.motivo.length === 0) {
				messages.error('Para petição inválidas, você precisa informar o motivo da inaptidão.');
				return;
			}
			
			PeticaoService.autuar(autuacao.idPeticao, new AutuarCommand(autuacao.classe, autuacao.valida, autuacao.motivo)).success(function(data) {
				$state.go('root.dashboard');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pôde ser autuada</b> porque ela não está válida.');
				}
			});
		};

    	function AutuarCommand(classe, valida, motivo){
    		var dto = {};
    		dto.classeId = classe;
    		dto.valida = valida;
    		dto.motivo = motivo;
    		return dto;
    	}
		
	});

})();

