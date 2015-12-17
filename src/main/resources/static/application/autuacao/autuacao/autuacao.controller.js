/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoController', function ($scope, $log, $state, $stateParams, messages, properties, ClasseService, PeticaoService) {
		var autuacao = this;
		
		var resource = $stateParams.resources[0];
		
		autuacao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		
		autuacao.classe = '';
		
		autuacao.valida = 'true';
		
		autuacao.motivo = '';
		
		autuacao.classes = [];
		
		autuacao.peticao = {};
		
		ClasseService.listar().success(function(classes) {
			autuacao.classes = classes;
		});
		
		PeticaoService.consultar(autuacao.peticaoId).then(function(data) {
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
			
			PeticaoService.autuar(autuacao.peticaoId, new AutuarCommand(autuacao.peticaoId, autuacao.classe, autuacao.valida, autuacao.motivo)).success(function(data) {
				$state.go('dashboard');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pôde ser autuada</b> porque ela não está válida.');
				}
			});
		};

    	function AutuarCommand(id, classe, valida, motivo){
    		var dto = {};
    		dto.peticaoId = id;
    		dto.classeId = classe;
    		dto.valida = valida;
    		dto.motivo = motivo;
    		return dto;
    	}
		
	});

})();

