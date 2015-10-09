/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('PesquisaPeticaoController', function ($scope, messages, classes, PesquisaService) {
		
		$scope.classes = classes.data;
		$scope.numero = null;
		$scope.ano = null;
		$scope.pessoa = null;
		$scope.classe = null;
		$scope.resultados = [];
		
		$scope.pesquisaPessoa = {
			texto : 'nome',
			indices : ['pessoa'],
			filtros : ['nome'],
			ordenadores: {'nome' : 'ASC'},
			pesquisa : 'sugestao'
		};
		
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
		
    	function PesquisarCommand(){
    		var dto = {};
    		
    		dto.indices = ['autuacao'];
    		dto.ordenadores = {'identificacao' : 'ASC'};
    		dto.filtros = {};
    		
    		if (angular.isNumber($scope.numero)) {
    			dto.filtros.numero = $scope.numero;
    		}
    		if (angular.isNumber($scope.ano)) {
    			dto.filtros.ano = $scope.ano;
    		}
    		if (angular.isString($scope.classe) && !$.isEmptyObject($scope.classe)) {
    			dto.filtros['classeSugerida.sigla'] = $scope.classe;
    		}
    		if (angular.isObject($scope.pessoa)) {
    			dto.filtros['partes.pessoaId.sequencial'] = $scope.pessoa.id;
    		}
    		return dto;
    	}
    	
	});
	
})();