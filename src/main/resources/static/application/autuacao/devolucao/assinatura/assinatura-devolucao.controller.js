/**
 * Assinatura do documento de devolução
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 04.12.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AssinaturaDevolucaoController', function($scope, $state, $stateParams, PeticaoService, PecaService, SignatureService, messages) {
		var resourcesToIds = function(resources) {
			var resourcesIds = [];
			angular.forEach(resources, function(resource) {
				if (typeof resource === 'object') {
					resourcesIds.push(resource.peticaoId);
				} else {
					resourcesIds.push(resource);
				}
			});
			return resourcesIds;
		};
		
		var idsPeticoes = resourcesToIds($stateParams.resources);
		
		var ID_TIPO_PECA_DEVOLUCAO = 8;
		
		$scope.peticoes = [];
		
		// Lógica de seleção de petições
		var selectAll = function() {
			$scope.selecao.peticoes = $scope.peticoes.map(function(peticao) {
				return peticao;
			});
		};
		
		var unselectAll = function() {
			$scope.selecao.peticoes = [];
		};
		
		$scope.checkToggle = true;
		
		$scope.toggleCheck = function() {
			if ($scope.checkToggle) {
				selectAll();
			} else {
				unselectAll();
			}
		};
		
		$scope.selecao = {
			'peticoes': []
		};
		
		var peticoesAssinadas = [];
		var peticoesComErroDuranteAssinatura = [];
		
		var AssinarDevolucaoCommand = function(peticaoId, documentoId) {
			this.peticaoId = peticaoId;
			this.documentoId = documentoId;
		};
		
		var signingManager = SignatureService.signingManager();
		
		angular.forEach(idsPeticoes, function(id) {
			PeticaoService.consultar(id).then(function(peticao) {
				$scope.peticoes.push(peticao);
				$scope.selecao.peticoes.push(peticao);
			});
		});
		
		$scope.pecaDocumentoDevolucao = function(peticao) {
			var pecaDevolucao;
			angular.forEach(peticao.pecas, function(peca) {
				if (peca.tipoId === ID_TIPO_PECA_DEVOLUCAO) {
					pecaDevolucao = peca;
				}
			});
			return pecaDevolucao;
		};
		
		$scope.urlConteudo = function(peca) {
			return PecaService.montarUrlConteudo(peca);
		};
		
		$scope.assinar = function() {
			if ($scope.selecao.peticoes.length == 0) {
				messages.error('É necessário selecionar pelo menos uma petição para assinar.');
			}
			angular.forEach($scope.selecao.peticoes, function(peticao) {
				var peca = $scope.pecaDocumentoDevolucao(peticao);
				var idDocumento = peca.documentoId;
				var signer = signingManager.createSigner();
				
				var progressTracker = signer.progressTracker(90); // 90% é o progresso final do passo de assinatura
				var lastStepFinished = false;
				
				// Função que calcula o progresso atual da assinatura
				peticao.progress = function() {
					if (lastStepFinished) {
						return 100;
					} else {
						return progressTracker.currentProgress();
					}
				};
				
				peticao.isFinished = function() {
					return lastStepFinished;
				};
				
				signer.onSignerReady(function(signerId) {
					signer.provideExistingDocument(idDocumento);
	            });
	            signer.onSigningCompleted(function() {
	            	signer.saveSignedDocument().then(function(savedSignedDocument) {
	            		var command = new AssinarDevolucaoCommand(peticao.id, savedSignedDocument.documentId);
	            		PeticaoService.assinarDevolucao([command]).then(function() {
	            			lastStepFinished = true;
	            			peticoesAssinadas.push(peticao);
	            			checarTerminoAssinatura();
	    				});
	            	});
	            });
	            signer.onErrorCallback(function(error) {
	            	messages.error('Erro ao assinar documento de devolução da Petição ' + petiao.id + '.');
	            	peticoesComErroDuranteAssinatura.push(peticao);
	            	checarTerminoAssinatura();
	            });
	            signer.start();
			});
		};
		
		var checarTerminoAssinatura = function() {
			if (peticoesAssinadas.length + peticoesComErroDuranteAssinatura.length == $scope.selecao.peticoes.length) {
				completar();
			}
		};
		
		$scope.progressoTotal = function() {
			return (peticoesAssinadas.length + peticoesComErroDuranteAssinatura.length) / (1.0 * $scope.selecao.peticoes.length);
		};
		
		var completar = function() {
			$state.go('dashboard');
			messages.success(peticoesAssinadas.length + ' documento(s) de devolução assinados com sucesso.');
		};
		
	});
	
})();

