/**
 * @author Anderson.Araujo
 * 
 * @since 07.03.2016
 */
(function() {
	'use strict';
	
	angular.autuacao.controller('EditaPecaController', function ($scope, $stateParams, $state, messages, properties, $window, $cookies, FileUploader, PeticaoService, DocumentoTemporarioService, ProcessoService) {
		$scope.peticaoId = angular.isObject($stateParams.resources[0]) ? $stateParams.resources[0].peticaoId : $stateParams.resources[0];
		$scope.processo = null;
		$scope.sequencialPeca = null;
		$scope.tipoPeca = null;
		$scope.descricaoPeca = null;
		$scope.visibilidade = null;
		$scope.visibilidades = [{id:"PUBLICO", descricao:"Público"}, {id:"PENDENTE_VISUALIZACAO", descricao:"Pendente de visualização"}];
		$scope.tiposPecas = [];
		$scope.recursos = [];

		PeticaoService.listarTipoPecas().then(function(tiposPecas) {
			$scope.tiposPecas = tiposPecas;
		});
		
		ProcessoService.consultarPorPeticao($scope.peticaoId).success(function(data){
			$scope.processo = data;
		});
	    
	    $scope.validar = function() {
	    	var errors = '';
	    	var camposErros = '';
	    	var pecaInserida = null;
			
			if ($scope.pecas.length === 0) {
				errors += 'Nenhuma peça foi informada.<br/>';
			} else {
				for (var i = 0; i < $scope.pecas.length; i++) {
					pecaInserida = $scope.pecas[i];
					
					if (pecaInserida.descricao == '' || pecaInserida.descricao == null) {
						camposErros += '- Descrição<br/>';
					}
					
					if (pecaInserida.visibilidade == null) {
						camposErros += '- Visibilidade<br/>';
					}
					
					if (pecaInserida.tipo == null) {
						camposErros += '- Tipo de peça<br/>';
					}
					
					if (camposErros != '') {
						errors += 'Campo(s) não informado(s) para a peça ' + pecaInserida.fileItem.file.name + ': <br/>' + camposErros + '<br/>';
					}
					
					if (errors) {
						messages.error(errors);
					}
				}
			}
			
	    	if (errors) {
				return false;
			}
	    	
	    	$scope.recursos[0] = new SalvarPecasCommand($scope.processo.id, $scope.pecas);
	    	
	    	return true;
	    };
	    
	    $scope.completar = function() {
	    	$state.go('organizar-pecas');
	    	messages.success('Peças inseridas com sucesso.');
	    };

    	function SalvarPecasCommand(processoId, pecas){
    		var dto = {};
    		
    		dto.processoId = processoId;
    		dto.pecas = montarDtoPecas(pecas);
    		
    		return dto;
    	};
		
    	function montarDtoPecas(pecas) {
    		var pecasDto = [];
    		
    		for (var i = 0; i < pecas.length; i++) {
    			var dto = {};
    			dto.documentoTemporarioId = pecas[i].documentoTemporario;
        		dto.tipoPecaId = pecas[i].tipo;
        		dto.visibilidade = pecas[i].visibilidade;
        		dto.situacao = 'Pendente de juntada';
        		dto.descricao = pecas[i].descricao;
        		
        		pecasDto.push(dto);
    		}
    		
    		return pecasDto;
    	};
	});

})();