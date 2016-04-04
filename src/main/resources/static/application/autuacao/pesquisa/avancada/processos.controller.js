/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('PesquisaProcessosAvancadaController', function ($scope, $stateParams, messages, PesquisaService) {
		
		$scope.isPesquisaNova = true;
		$scope.elasticBuilderData = { fields : {}, query : {}};
		$scope.indices = [];
		$scope.pesquisa = {
			tipo : 'PROCESSO',
    		indices : $scope.indices,
    		consulta : $scope.elasticBuilderData.query
		};
				
		var carregarCampos = function(indices) {
			angular.forEach(indices, function(indice) {
				$scope.indices.push(indice);
			});
			
			PesquisaService.carregarCampos($scope.elasticBuilderData.fields, indices)
				.then(function() {
					$scope.elasticBuilderData.needsUpdate = true;
				});
		};
				
		if (angular.isDefined($stateParams.pesquisaId)) {	
			$scope.isPesquisaNova = false;
			$scope.pesquisa.pesquisaId = $stateParams.pesquisaId;
			
			PesquisaService.consultar($stateParams.pesquisaId)
				.then(function(result) {
					$scope.pesquisa.nome = result.data.nome;
					$scope.elasticBuilderData.query = result.data.consulta;
					carregarCampos(result.data.indices);
				}, function() {
					carregarCampos(["distribuicao"]);
				});
		} else {
			carregarCampos(["distribuicao"]);
		}
		
		$scope.$watch('elasticBuilderData.query', function() {
			$scope.pesquisa.consulta = $scope.elasticBuilderData.query;
		});
		
		$scope.pesquisar = function() {
			
			var command = new PesquisarCommand();
			if ($.isEmptyObject(command.consulta) || 
					$scope.elasticBuilderData.query.query.constant_score.filter.and.length == 0) {
				messages.error('Informe pelo menos um filtro para pesquisa!');
				return;
			}
			console.log(command.consulta);
			PesquisaService.pesquisarAvancado(command)
				.then(function(resultados) {
					$scope.resultados = resultados.data.content;
				}, function(data, status) {
					messages.error('Ocorreu um erro e a pesquisa não pode ser realizada!');
				});
		};
		
		function PesquisarCommand(consulta, indices) {
			var command = {};
			command.consulta = JSON.stringify($scope.elasticBuilderData.query);
			command.indices = $scope.indices;
			command.page = 0;
			command.size = 15;
			return command;
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