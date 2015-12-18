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
		
		preautuacao.peticaoId = $stateParams.resources[0];
		
		preautuacao.valida = 'true';
		
		preautuacao.motivo = '';

		preautuacao.classe = "";
		
		preautuacao.classes = [];
		
		preautuacao.peticao = {};
		
		preautuacao.recursos = [{}];
		
		PeticaoService.consultar(preautuacao.peticaoId).then(function(data) {
			preautuacao.peticao = data;
		});
		
		ClasseService.listar().success(function(classes) {
			preautuacao.classes = classes;
		});
		
		preautuacao.validar = function(){
			var errors = null;
			
			if (preautuacao.classe.length === 0) {
				errors = 'Você precisa selecionar <b>a classe processual sugerida</b>.';
			}
			
			if (preautuacao.valida === 'false' && preautuacao.motivo.length === 0) {
				errors += 'Para petição incorretas, você precisa informar os detalhes do motivo.';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			
			preautuacao.recursos[0] = new PreautuarCommand(preautuacao.peticaoId, preautuacao.classe, preautuacao.valida, preautuacao.motivo);
			
			return true;
		};
		
		preautuacao.finalizar = function() {
			$state.go('dashboard');
			messages.success('Petição pré-autuada com sucesso.');
		};
		
    	function PreautuarCommand(peticaoId, classeId, valida, motivo){
    		var command = {};
    		command.peticaoId = peticaoId;
    		command.classeId = classeId;
    		command.valida = valida;
    		command.motivo = motivo;
    		return command;
    	}
    	
	});

})();

