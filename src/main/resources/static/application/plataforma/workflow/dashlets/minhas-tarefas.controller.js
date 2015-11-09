/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('MinhasTarefasDashletController', ['$scope', 'TarefaService', 'PesquisaService', function($scope, TarefaService, PesquisaService) {
		
		TarefaService.listar().success(function(tarefas) {
			$scope.tarefas = tarefas;
			angular.forEach(tarefas, function(tarefa) {
				var command = PesquisarCommand(tarefa.metadado);
				PesquisaService.pesquisar(command)
				.then(function(resultados) {
					tarefa.texto = resultados.data[0].objeto.identificacao;
				});
			});
		});
		
    	function PesquisarCommand(metadado){
    		var dto = {};
    		dto.indices = ['autuacao', 'distribuicao'];
    		dto.tipos = [metadado.tipoInformacao];
    		dto.campos = ['identificacao'];
    		dto.filtros = { 'id.sequencial' : metadado.informacao };

    		return dto;
    	}
		
	}]);
	
})();

