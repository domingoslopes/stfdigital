/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.autuacao.controller('DividePecasController', function($scope, $stateParams, messages, PecaService, PeticaoService) {
		
		var resource = $stateParams.resources[0];
		
		var documento = null;
		
		var pecasId = [];
		
		var tamanhoTotalPeca = 0;
		
		var pecasProcessual = [];
		
		$scope.intervalos = [];
		
		$scope.inicioPagina;
		
		$scope.fimPagina;
		
		$scope.messages={};
		
		PecaService.consultarDocumento(resource.peca.documentoId).then(function(data){
			documento = data;
			$scope.numeroPaginas = data.quantidadePaginas;
		});
		
		PeticaoService.listarTipoPecas().then(function(tiposPeca) {
			$scope.tiposPeca = tiposPeca;
		});
		
		$scope.adicionarIntervaloDivisao = function(){
			if ($scope.intervalos.length < 1){
				if ($scope.fimPagina > $scope.numeroPaginas){
					$scope.messages.api.warning("Número final da página é maior que o numero total de páginas do documento. ");
					return;
				}
			}else{
				for (var i in $scope.intervalos){
					var intervalo = $scope.intervalos[i];
					if (($scope.inicioPagina < intervalo.paginaFinal) || ($scope.fimPagina > $scope.numeroPaginas)){
						$scope.messages.api.warning("A página inicial ou pagina final inconsistentes.");
						return;
					}
				}
			}
			$scope.intervalos.push({'tipoPecaId': $scope.tipoPeca, 'visibilidade': resource.peca.visibilidade, 'situacao': resource.peca.situacao, 'descricao': $scope.descricao, 
				'paginaInicial': $scope.inicioPagina, 'paginaFinal' : $scope.fimPagina });
			limparCampos();
			
		};
		
		$scope.removerIntervalo = function(intervaloSelecionado){
			var i;
			for (i = 0 ; i < $scope.intervalos.length ; i++){
				if (($scope.intervalos[i].paginaInicial == intervaloSelecionado.paginaInicial)
					&& ($scope.intervalos[i].paginaFinal == intervaloSelecionado.paginaFinal)){
					$scope.intervalos.splice(i);
				}
			}
		};
		
		var limparCampos = function(){
			$scope.descricao= "";
			$scope.inicioPagina= "";
			$scope.fimPagina= "";
			$scope.tipoPeca= "";
		}
		
		$scope.commands = [{
			processoId : resource.processoId, 
			pecaId : resource.peca.sequencial,
			pecas : $scope.intervalos
		}];
		
		$scope.validar = function() {
			messages.success('<b> Peças divididas com sucesso </b>');
			return true;
		};
		
	});
	
})();

