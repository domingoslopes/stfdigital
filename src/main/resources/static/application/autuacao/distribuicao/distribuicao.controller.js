/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('DistribuicaoController', function (data, $scope, $stateParams, messages, properties, $state, PeticaoService, PesquisaService) {
		
		$scope.idPeticao = $stateParams.idTarefa;
		
		$scope.ministros = data.data;
		
		$scope.relator = '';
		
		$scope.partes = [];

		var partesPeticao = {};
		
		var peticao;
		
		var commandPartesPeticao;
		
		PeticaoService.consultarPartes($scope.idPeticao).success(function(partesP) {
			partesPeticao = partesP;
			commandPartesPeticao = new PartesPeticaoCommand(partesPeticao);
			
			PesquisaService.pesquisar(commandPartesPeticao).then(function(resultados) {
				$scope.partes = resultados.data;
			}, function(resultados, status) {
				messages.error('Ocorreu um erro e a pesquisa não pode ser realizada!');
			});
		});
		
		$scope.consultaProcesso = function(IdPessoa){
			var commandProcessosPessoa = new ProcessosPessoaCommand(idPessoa);
			PesquisaService.pesquisar(commandProcessosPessoa).then(function(processos){
				$scope.processosParte = processos.data;
			}, function(processos,status){
				messages.error('Ocorreu um erro e a pesquisa de processsos da parte não pode ser realizada!');
			});
		};
		
		
		$scope.finalizar = function() {
			if ($scope.relator.length === 0) {
				messages.error('Você precisa selecionar um <b>ministro relator</b> para o processo.');
				return;
			}
			
			var command = new DistribuirCommand($scope.relator);
			
			PeticaoService.distribuir($scope.idPeticao, command).success(function(data) {
				$state.go('dashboard');
				//messages.success('<b>' + data.classe + ' #' + data.numero + '</b> distribuída para <b>' + data.relator + '</b>');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pode ser registrada</b> porque ela não está válida.');
				}
			});
		};
		
    	function DistribuirCommand(ministroId){
    		var dto = {};
    		
    		dto.ministroId = ministroId;
    		
    		return dto;
    	}
    	
    	function PartesPeticaoCommand(partesPeticao){
    		var dto = {};
    		var idsPartes = [];
    		
    		idsPartes = partesPeticao.PoloAtivo.concat(partesPeticao.PoloPassivo);
    		
    		dto.indices = ['pessoa'];
    		dto.campos = ['id.sequencial', 'nome'];
    		dto.ordenadores = {'nome' : 'ASC'};
    		dto.filtros = { 'id.sequencial': idsPartes };

    		return dto;
    	};
    	
    	function PartesPeticaoCommand(partesPeticao){
    		var dto = {};
    		var idsPartes = [];
    		
    		idsPartes = partesPeticao.PoloAtivo.concat(partesPeticao.PoloPassivo);
    		
    		dto.indices = ['pessoa'];
    		dto.campos = ['id.sequencial', 'nome'];
    		dto.ordenadores = {'nome' : 'ASC'};
    		dto.filtros = { 'id.sequencial': idsPartes };

    		return dto;
    	};
    	
	});
	
})();