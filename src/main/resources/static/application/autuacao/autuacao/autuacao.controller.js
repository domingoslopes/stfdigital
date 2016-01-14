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
		autuacao.recursos = [];
		
		ClasseService.listar().success(function(classes) {
			autuacao.classes = classes;
		});
		
		PeticaoService.consultar(autuacao.peticaoId).then(function(data) {
			autuacao.peticao = data;
		});
		
		autuacao.validar = function() {
			var errors = null;
			if (autuacao.classe.length === 0) {
				errors = 'Você precisa selecionar <b>a classe processual definitiva</b>.<br/>';
			}
			
			if (autuacao.valida === 'false' && autuacao.motivo.length === 0) {
				errors += 'Para petição inválidas, você precisa informar o motivo da inaptidão.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			autuacao.recursos.push(new AutuarCommand(autuacao.peticaoId, autuacao.classe, autuacao.valida, autuacao.motivo));
			return true;
		}
		
		autuacao.completar = function() {
			$state.go('dashboard');
			messages.success('Petição <b>' + autuacao.peticao.identificacao + '</b> autuada com sucesso.');
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

