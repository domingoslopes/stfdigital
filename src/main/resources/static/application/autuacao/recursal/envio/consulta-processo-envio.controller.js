/**
 * @author Anderson.araujo
 * 
 * @since 1.0.0
 * @since 04.04.2016
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('ConsultaProcessoEnvioController', function ($scope, $state, $stateParams, messages, properties) {
		
		$scope.processos = [];
		$scope.chaveProcessoSelecionada = [];
		
		$scope.listarProcessosSalvos = function(){
			for (var i = 0; i < Object.keys(localStorage).length; i++){
				$scope.processos.push({chave:Object.keys(localStorage)[i], isSelecionado:false});
			}
		};
		
		$scope.listarProcessosSalvos();
		
		$scope.selecionarProcesso = function(processo){
			$scope.chaveProcessoSelecionada[0] = processo.chave;
		};
		
		$scope.removerProcesso = function(processo){
			var indice = 0;
			
			for(var i = 0; i < $scope.processos.length; i++){
				if ($scope.processos[i].chave == processo.chave){
					indice = i;
					break;
				}
			}
			
			$scope.processos.splice(indice, 1);
			localStorage.removeItem(processo.chave);
		};
	});

})();