/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PreautuacaoController', function ($log, $http, $state, $stateParams, messages, properties, ClasseService, PeticaoService) {
		var preautuacao = this;
		
		preautuacao.idPeticao = $stateParams.idTarefa;
		
		preautuacao.valida = 'true';
		
		preautuacao.motivo = '';

		preautuacao.classe = "";
		
		preautuacao.classes = [];
		
		preautuacao.peticao = {};
		
		PeticaoService.consultar(preautuacao.idPeticao).success(function(data) {
			preautuacao.peticao = data;
		});
		
		ClasseService.listar().success(function(classes) {
			preautuacao.classes = classes;
		});
		
		preautuacao.finalizar = function() {
			if (preautuacao.classe.length === 0) {
				messages.error('Você precisa selecionar <b>a classe processual sugerida</b>.');
				return;
			}
			
			if (preautuacao.valida === 'false' && preautuacao.motivo.length === 0) {
				messages.error('Para petição incorretas, você precisa informar os detalhes do motivo.');
				return;
			}
			
			PeticaoService.preautuar(preautuacao.idPeticao, new PreautuarCommand(preautuacao.classe, preautuacao.valida, preautuacao.motivo)).success(function(data) {
				$state.go('dashboard');
				messages.success('Petição pré-autuada com sucesso.');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pôde ser pré-autuada</b> porque ela não está válida.');
				}
			});
		};
		
    	function PreautuarCommand(classeId, valida, motivo){
    		var command = {};
    		command.classeId = classeId;
    		command.valida = valida;
    		command.motivo = motivo;
    		return command;
    	}
    	
	});

})();

