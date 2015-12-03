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
		
		$scope.ministrosCandidados = data.data;
		
		$scope.relator = '';
		
		$scope.partes = [];
		
		$scope.processosParte = [];
		
		$scope.nomeParteRelacionada = '';
		
		$scope.tiposDistribuicao = [{id: 'COMUM', nome: 'Comum'}, {id: 'PREVENCAO', nome: 'Prevenção Relator/Sucessor'}];
		
		$scope.ministrosImpedidos = [];
		
		$scope.processo;
		
		$scope.tipoDistribuicao;
		
		$scope.processosPreventos = [];
		
		$scope.justificativa = '';
		
		$scope.$watch('processo', function(novo){
			if (novo){
				$scope.adicionarProcessoPreventoLista(novo);
			}
		});
		
		$scope.exibePainelMinistroImpedimento = false;
		
		$scope.exibeJustificativa = false;
		
		$scope.exibePesquisaProcessoPrevencao = false;

		var partesPeticao = {};
		
		var peticao;

		var commandPartesPeticao;
		
		$scope.pesquisaProcesso = {
				texto : 'identificacao',
				indices : ['distribuicao'],
				filtros : ['identificacao'],
				ordenadores: {'identificacao' : 'ASC'},
				pesquisa : 'sugestao'
			};
		
		
		$scope.adicionarProcessoPreventoLista = function(processo){
			var verificaSeProcessoExiste = false;
			angular.forEach($scope.processosPreventos, function(processoP) {
				if (processoP.id == processo.id) {
					verificaSeProcessoExiste = true;
				}
				if (processoP.objeto['relator.codigo'] != processo.objeto.relator.codigo){
					verificaSeProcessoExiste = true
					messages.error('Este processo possui um relator diferente do processo selecionado anteriormente!');
				}
			});
			if (!verificaSeProcessoExiste){
				$scope.processosPreventos.push(processo);
				$scope.exibePesquisaProcessoPrevencao = true;
				$scope.processo = null;
			}
		};
		
		$scope.removerProcessoPreventoLista = function($index){
			$scope.processosPreventos.splice($index,1);
		}
		
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
		
		$scope.consultarProcesso = function(parte){
			$scope.nomeParteRelacionada = parte.objeto.nome;
			var commandProcessosPessoa = new ProcessosDaParteCommand(parte.id);
			PesquisaService.pesquisar(commandProcessosPessoa).then(function(processos){
				parte.processosParte = processos.data;
			}, function(processos,status){
				messages.error('Ocorreu um erro e a pesquisa de processsos da parte não pode ser realizada!');
			});
		};
		
		$scope.verificaImpedimentoMinistro = function(tipoNome){
			if (tipoNome == 'COMUM'){
				$scope.exibePainelMinistroImpedimento = true;
				$scope.exibePesquisaProcessoPrevencao = false;
				$scope.processosParte = [];
			}else if (tipoNome == 'PREVENCAO'){
				$scope.exibePesquisaProcessoPrevencao = true;
				$scope.exibePainelMinistroImpedimento = false;
				$scope.ministrosCandidados = [];
				$scope.ministrosImpedidos = [];
			}else{
				$scope.exibePainelMinistroImpedimento = false;
				$scope.exibePesquisaProcessoPrevencao = false;
			}
		}
		
		$scope.formatarNomeMinistro = function(data){
			return data.nome;
		}
		
		
		$scope.finalizar = function() {
			
			//Caso o usuário selecione o tipo de distribuição "Comum", a lista de ministros candidatos não
			//deve estar vazia
			if ($scope.tipoDistribuicao == 'COMUM'){
				if($scope.ministrosCandidados.length == 0){
					messages.error('A lista deve possuir ao menos um Ministro para sorteio.');
					return;
				}
			}
			
			//Caso o usuário selecione o tipo de distribuição "Prevenção", será necessário existir pelo menos
			// um processo adicionado
			if ($scope.tipoDistribuicao == 'PREVENCAO'){
				if ($scope.processosPreventos.length == 0){
					messages.error('Você precisa adicionar ao menos um processo na lista de preventos.');
					return;
				}
			}
			
			//A justificativa deve ser obrigatória quando o tipo de distribuição for 'PREVENCAO'
			if ($scope.justificativa.length == 0 && $scope.tipoDistribuicao == 'PREVENCAO' ){
				messages.error('Você precisa inserir uma justificativa.');
				return;
			}
			
			//Se o tipo de distribuição for 'PREVENCAO' a lista de ministros candidatos e impedidos
			//deverão estar vazias já que o relator do processo prevento será o relator da petição
			if ($scope.tipoDistribuicao == 'PREVENCAO'){
				$scope.ministrosCandidados = [];
				$scope.ministrosImpedidos = [];
			}
			
			var command = new DistribuirCommand($scope.tipoDistribuicao, $scope.idPeticao, $scope.justificativa, 
					$scope.ministrosCandidados, $scope.ministrosImpedidos, $scope.processosPreventos);
			
			PeticaoService.distribuir($scope.idPeticao, command).success(function(data) {
				$state.go('root.dashboard');
				//messages.success('<b>' + data.classe + ' #' + data.numero + '</b> distribuída para <b>' + data.relator + '</b>');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pode ser registrada</b> porque ela não está válida.');
				}
			});
		};
		
		
    	function DistribuirCommand(idTipoDistribuicao, idPeticao, justificativa, ministrosCandidatos, 
    			ministrosImpedidos, processosPreventos){
    		var dto = {};
    		dto.ministrosCandidatos = [];
    		dto.ministrosImpedidos = [];
    		dto.processosPreventos = [];
			dto.tipoDistribuicao = idTipoDistribuicao;
			dto.peticaoId = parseInt(idPeticao);
			dto.justificativa = justificativa;
			
			if (ministrosCandidatos.length > 0){
				angular.forEach(ministrosCandidatos, function(ministrosC) {
					dto.ministrosCandidatos.push(ministrosC.codigo)
				});	
			}
			
			if (ministrosImpedidos.length > 0){
				angular.forEach(ministrosImpedidos, function(ministrosI) {
					dto.ministrosImpedidos.push(ministrosI.codigo)
				});	
			}
			
			if (processosPreventos.length > 0){
				angular.forEach(processosPreventos, function(processosP) {
					dto.processosPreventos.push(processosP.id)
				});	
			}
			
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
    		dto.campos = ['id.sequencial', 'identificacao', 'relator.codigo'];
    		dto.ordenadores = {'id.sequencial' : 'ASC'};
    		dto.filtros = { 'partes.pessoaId.sequencial': [idPessoa] };

    		return dto;
    	}
    	
	});
	
})();