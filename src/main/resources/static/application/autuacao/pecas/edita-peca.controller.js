/**
 * @author Anderson.Araujo
 * 
 * @since 07.03.2016
 */
(function() {
	'use strict';
	
	angular.autuacao.controller('EditaPecaController', function ($scope, $stateParams, $state, messages, properties, $window, $cookies, FileUploader, PeticaoService, DocumentoTemporarioService, ProcessoService) {
		$scope.processoId = $stateParams.resources[0].processoId; 
		$scope.pecaId = $stateParams.resources[0].peca.sequencial;
		$scope.sequencialPeca = $stateParams.resources[0].peca.numeroOrdem;
		$scope.tipoPeca = $stateParams.resources[0].peca.tipoId;
		$scope.descricaoPeca = $stateParams.resources[0].peca.descricao;
		$scope.visibilidade = $stateParams.resources[0].peca.visibilidade;
		$scope.visibilidades = [{id:"PUBLICO", descricao:"Público"}, {id:"PENDENTE_VISUALIZACAO", descricao:"Pendente de visualização"}];
		$scope.tiposPecas = [];
		$scope.recursos = [];
		
		$scope.messages = {};

		PeticaoService.listarTipoPecas().then(function(tiposPecas) {
			$scope.tiposPecas = tiposPecas;
		});
	    
	    $scope.validar = function() {
	    	var errors = '';
	    	var camposErros = '';
	    	var pecaInserida = null;
			
			if ($scope.sequencialPeca == null) {
				messages.error('Informe o sequencial da peça.');
				return false;
			}
			
			if ($scope.tipoPeca == null) {
				messages.error('Informe o tipo de peça.');
				return false;
			}
			
			if ($scope.descricaoPeca == null) {
				messages.error('Informe descrição da peça.');
				return false;
			}
			
			if ($scope.visibilidade == null) {
				messages.error('Informe a visibilidade da peça.');
				return false;
			}
	    	
	    	$scope.recursos[0] = new editarPecaCommand($scope.processoId, $scope.pecaId, $scope.tipoPeca, $scope.descricaoPeca, $scope.sequencialPeca, $scope.visibilidade);
	    	
	    	return true;
	    };
	    
	    $scope.completar = function() {
	    	$state.go('organizar-pecas');
	    	messages.success('Peça editada com sucesso.');
	    };

	    $scope.tratarErro = function(error) {
	    	$scope.messages.api.error(error.errors[0].message);
	    };
	    
    	function editarPecaCommand(processoId, pecaId, tipoPecaId, descricao, numeroOrdem, visibilidade){
    		var dto = {};
    		
    		dto.processoId = processoId;
    		dto.pecaId = pecaId;
    		dto.tipoPecaId = tipoPecaId;
    		dto.descricao = descricao;
    		dto.numeroOrdem = numeroOrdem;
    		dto.visibilidade = visibilidade;
    		
    		return dto;
    	};
	});

})();