/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PreautuacaoController', function ($log, $http, $state, $stateParams, properties, messages, ClasseService, PeticaoService) {
		var preautuacao = this;
		
		preautuacao.idPeticao = $stateParams.idTarefa;
		
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
			
			var command = new PreautuarCommand(preautuacao.idPeticao, preautuacao.classe);
			
			PeticaoService.preautuar(preautuacao.idPeticao, command).success(function(data, status, headers, config) {
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		
	    	function PreautuarCommand(peticaoId, classeId){
	    		var dto = {};
	    		dto.peticaoId = peticaoId;
	    		dto.classeId = classeId;
	    		return dto;
	    	}	

		};
	});

})();

