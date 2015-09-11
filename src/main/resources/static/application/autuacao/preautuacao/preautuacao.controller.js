/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PreautuacaoController', function ($log, $http, $state, $stateParams, properties, ClasseService, PeticaoService) {
		var preautuacao = this;
		
		preautuacao.idPeticao = $stateParams.idTarefa;
		
		preautuacao.classe = "";
		
		preautuacao.classes = [];
		
		preautuacao.peticao = {};
		
		PeticaoService.consultarPeticaoFisica(preautuacao.idPeticao).success(function(data) {
			preautuacao.peticao = data;
		});
		
		ClasseService.listar().success(function(classes) {
			preautuacao.classes = classes;
		});
		
		preautuacao.finalizar = function() {
			if (preautuacao.classe.length === 0) {
				messages.error('Você precisa selecionar <b>a classe processual definitiva</b>.');
				return;
			}
			
			$http.post(properties.apiUrl + '/peticao/' + preautuacao.idPeticao + '/preautuacao').success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('dashboard');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

