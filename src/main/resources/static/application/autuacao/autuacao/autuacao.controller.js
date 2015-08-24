/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoController', function ($log, $http, $state, $stateParams, messages, properties, ClasseService) {
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
		
		$http.get(properties.apiUrl + '/peticao/' + autuacao.idPeticao).success(function(data, status, headers, config) {
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
			
			$http.post(properties.apiUrl + '/peticao/' + autuacao.idPeticao + '/autuacao', {classe: autuacao.classe, valida:autuacao.valida, motivo:autuacao.motivo}).success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

