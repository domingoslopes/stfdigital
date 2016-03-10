/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('PesquisaProcessosAvancadaController', function ($scope, $stateParams, messages, PesquisaService) {
		
		$scope.elasticBuilderData = { fields : {}, query : []};
		$scope.indices = [];
		$scope.recursos = [{
    		indices : $scope.indices,
    		consulta : $scope.elasticBuilderData.query
		}];
				
		var carregarCampos = function(indices) {
			angular.forEach(indices, function(indice) {
				$scope.indices.push(indices);
			});
			
			PesquisaService.carregarCampos($scope.elasticBuilderData.fields, indices)
				.then(function() {
					$scope.elasticBuilderData.needsUpdate = true;
				});
		};
		//{ query : { constant_score : { filter : { and : filters }}}};
				
		if (angular.isDefined($stateParams.pesquisa)) {	
			PesquisaService.consultar($stateParams.pesquisa)
				.then(function(result) {
					$scope.elasticBuilderData.query = result.data.consulta;
					carregarCampos(result.data.indices);
				});
		} else {
			carregarCampos(["distribuicao"]);
		}
		
		$scope.pesquisar = function() {
			
			var command = new PesquisarCommand();
			if ($.isEmptyObject(command.filtros)) {
				messages.error('Informe pelo menos um filtro para pesquisa!');
				return;
			}
			PesquisaService.pesquisar(command)
				.then(function(resultados) {
					$scope.resultados = resultados.data;
				}, function(data, status) {
					messages.error('Ocorreu um erro e a pesquisa não pode ser realizada!');
				});
		};
		
    	function SalvarPesquisaCommand() {
    		var dto = {};

    		
    		return dto;
    	}
		
		$scope.showQuery = function() {
			return JSON.stringify($scope.elasticBuilderData.query, null, 2);
		};
		
		$scope.resultados = [];
		
		$scope.buildSelectedObject = function(item){
			return {'processoId': item.id};
		};
		
		$scope.toggleCheck = function() {
			if ($scope.checkToggle) {
				$scope.selecao.objetos = $scope.resultados.map(function(item) {
					return $scope.buildSelectedObject(item);
				});
			} else {
				$scope.selecao.objetos = [];
			}
		};
		
		$scope.selecao = {
			objetos: []
		};
    	
	});
	
})();