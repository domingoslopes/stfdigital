/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('PesquisaProcessosController', function ($scope, messages, classes, PesquisaService, MinistroService, ProcessoService) {
		
		$scope.classes = classes.data;
		$scope.ministros = [];
		$scope.numero = null;
		$scope.ministro = null;
		$scope.pessoa = null;
		$scope.classe = null;
		$scope.peticao = null;
		$scope.resultados = [];
		
		MinistroService.listar().success(function(ministros) {
			$scope.ministros = ministros;
		});
		
		$scope.situacoes = ProcessoService.listarStatus();
		
		/*ProcessoService.listarStatus().success(function(situacoes){
			$scope.situacoes = situacoes;
		});*/
		
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
    		
    		dto.indices = ['distribuicao'];
    		dto.ordenadores = {'identificacao' : 'ASC'};
    		dto.filtros = {};
    		
    		if (angular.isNumber($scope.numero)) {
    			dto.filtros.numero = [$scope.numero];
    		}
    		var codigo = parseInt($scope.ministro);
     		if (isFinite(codigo)) {
    			dto.filtros['relator.codigo'] = [codigo];
    		}
    		if (angular.isString($scope.classe) && !$.isEmptyObject($scope.classe)) {
    			dto.filtros['classe.sigla'] = [$scope.classe];
    		}
    		if (angular.isObject($scope.pessoa)) {
    			dto.filtros['partes.pessoaId.sequencial'] = [$scope.pessoa.id];
    		}
    		return dto;
    	}
    	
	});
	
})();