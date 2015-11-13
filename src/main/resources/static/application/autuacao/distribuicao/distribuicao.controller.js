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
		
		$scope.processosParte = [];
		
		$scope.nomeParteRelacionada = '';

		var partesPeticao = {};
		
		var peticao;
		
		//var idPessoa;
		
		var commandPartesPeticao;
		
		
		PeticaoService.consultarPartes($scope.idPeticao).success(function(partesP) {
			partesPeticao = partesP;
			
			var idsPartes = partesPeticao.PoloAtivo.concat(partesPeticao.PoloPassivo);
			
			if (idsPartes.length > 0){
				commandPartesPeticao = new PartesPeticaoCommand(idsPartes);
				
				PesquisaService.pesquisar(commandPartesPeticao).then(function(resultados) {
					$scope.partes = resultados.data;
				}, function(resultados, status) {
					messages.error('Ocorreu um erro e a pesquisa não pode ser realizada!');
				});	
			}
			
		});
		
		$scope.consultarProcesso = function(idPessoa, nomeParte){
			$scope.nomeParteRelacionada = nomeParte;
			var commandProcessosPessoa = new ProcessosDaParteCommand(idPessoa);
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
    	
    	function PartesPeticaoCommand(idsPartes){
    		var dto = {};
    		
    		dto.indices = ['pessoa'];
    		dto.campos = ['id.sequencial', 'nome'];
    		dto.ordenadores = {'nome' : 'ASC'};
    		dto.filtros = { 'id.sequencial': idsPartes };

    		return dto;
    	}
    	
    	function ProcessosDaParteCommand(idPessoa){
    		var dto = {};
    		
    		dto.indices = ['distribuicao'];
    		dto.campos = ['id.sequencial', 'identificacao'];
    		dto.ordenadores = {'id.sequencial' : 'ASC'};
    		dto.filtros = { 'partes.pessoaId.sequencial': [idPessoa] };

    		return dto;
    	}
    	
	});
	
})();