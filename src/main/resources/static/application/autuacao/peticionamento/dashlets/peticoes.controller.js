/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('MinhasPeticoesDashletController', ['$scope', 'PesquisaService', 'SecurityService', function($scope, PesquisaService, SecurityService) {
		$scope.peticoes = [];
		
		var pesquisar = function() {
			
			var command = new PesquisarPeticoesCommand();
			
			PesquisaService.pesquisar(command)
				.then(function(resultados) {
					$scope.peticoes = resultados.data;
					pesquisarProcessos($scope.peticoes);
				}, function(data, status) {
					messages.error('Ocorreu um erro e a pesquisa não pode ser realizada!');
				});
		};
		
    	function PesquisarPeticoesCommand(){
    		var dto = {};
    		
    		dto.indices = ['autuacao'];
    		dto.campos = ['identificacao', 'dataCadastramento'];
    		dto.ordenadores = {'identificacao' : 'ASC'};
    		dto.filtros = {
    			'usuarioCadastramento': [SecurityService.user().login]
    		};

    		return dto;
    	}
    	
    	function PesquisarProcessosCommand(peticoes){
    		var dto = {};
    		var idsPeticoes = [];
    		
    		angular.forEach(peticoes, function(peticao) {
    			idsPeticoes.push(peticao.id);
    		});
    		
    		dto.indices = ['distribuicao'];
    		dto.campos = ['identificacao', 'peticao.sequencial'];
    		dto.ordenadores = {'identificacao' : 'ASC'};
    		dto.filtros = { 'peticao.sequencial': idsPeticoes };

    		return dto;
    	}
    	
    	function pesquisarProcessos(peticoes) {
			if (!angular.isArray(peticoes) || $scope.peticoes.length === 0) {
				return;	
			}    		
			var command = new PesquisarProcessosCommand(peticoes);
			PesquisaService.pesquisar(command).then(function(resultadosProcessos) {
				var processos = resultadosProcessos.data;
				angular.forEach(peticoes, function(peticao) {						
					angular.forEach(processos, function(processo) {
						if (peticao.id == processo.objeto['peticao.sequencial']) {
							peticao.processo = processo.objeto.identificacao;
						} 
					});
				});
			});
    	}
    	
    	pesquisar();

	}]);

})();

