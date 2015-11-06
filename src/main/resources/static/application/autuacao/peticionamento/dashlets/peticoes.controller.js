/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('MinhasPeticoesDashletController', ['$scope', 'PesquisaService', '$window', function($scope, PesquisaService, $window) {
		$scope.peticoes = [];
		var pesquisar = function() {
			
			var command = new PesquisarCommand();
			
			PesquisaService.pesquisar(command)
				.then(function(resultados) {
					$scope.peticoes = resultados.data;
				}, function(data, status) {
					messages.error('Ocorreu um erro e a pesquisa não pode ser realizada!');
				});
		};
		
    	function PesquisarCommand(){
    		var dto = {};
    		
    		dto.indices = ['autuacao'];
    		dto.campos = ['identificacao', 'dataCadastramento', 'classeProcessual.sigla', 'numero'];
    		dto.ordenadores = {'identificacao' : 'ASC'};
    		dto.filtros = {
    				usuarioCadastramento: JSON.parse($window.sessionStorage.papel).nome
    		};

    		return dto;
    	}
    	
    	pesquisar();

	}]);

})();

