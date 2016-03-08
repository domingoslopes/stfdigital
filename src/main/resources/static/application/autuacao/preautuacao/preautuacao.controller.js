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
		
		var resource = $stateParams.resources[0];
		preautuacao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		preautuacao.motivo = '';
		preautuacao.classe = "";
		preautuacao.classes = [];
		preautuacao.peticao = {};
		preautuacao.recursos = [];
		preautuacao.preferenciasSelecionadas = [];
		preautuacao.preferencias = [];
		
		preautuacao.carregarPreferencias = function() {
			ClasseService.consultarPreferencias(preautuacao.classe).success(function(preferencias) {
				preautuacao.preferenciasSelecionadas = [];
				preautuacao.preferencias = preferencias;
			});
		};
		
		PeticaoService.consultar(preautuacao.peticaoId).then(function(data) {
			preautuacao.peticao = data;
		});
		
		ClasseService.consultarPorTipoProcesso('originario').success(function(classes) {
			preautuacao.classes = classes;
		});
			
		preautuacao.validar = function(){
			var errors = null;
			
			if (preautuacao.classe.length === 0) {
				errors = 'Você precisa selecionar <b>a classe processual sugerida</b>.';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			
			preautuacao.recursos.push(new PreautuarCommand(preautuacao.peticaoId, preautuacao.classe, true, preautuacao.motivo, preautuacao.preferenciasSelecionadas));
			return true;
		};
		
		preautuacao.validarDevolucao = function(){
			var errors = null;
			
			if (preautuacao.motivo.length === 0) {
				errors = 'Para petição incorreta, você precisa informar os detalhes do motivo.';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			
			preautuacao.recursos.push(new PreautuarCommand(preautuacao.peticaoId, preautuacao.classe, false, preautuacao.motivo, preautuacao.preferenciasSelecionadas));
			return true;
		};
		
		preautuacao.devolver = function(){
			preautuacao.recursos.push(new PreautuarCommand(preautuacao.peticaoId, preautuacao.classe, false, preautuacao.motivo, preautuacao.preferenciasSelecionadas));
			return true;
		};
		
		preautuacao.finalizar = function() {
			$state.go('dashboard');
			messages.success('Petição <b>' + preautuacao.peticao.identificacao + '</b> pré-autuada com sucesso.');
		};
		
		preautuacao.finalizarDevolucao = function() {
			$state.go('dashboard');
			messages.success('Petição devolvida com sucesso.');
		};
		
    	function PreautuarCommand(peticaoId, classeId, valida, motivo, preferencias){
    		var command = {};
    		command.peticaoId = peticaoId;
    		command.classeId = classeId;
    		command.valida = valida;
    		command.motivo = motivo;
    		command.preferencias = preferencias;
    		return command;
    	}
	});
})();
