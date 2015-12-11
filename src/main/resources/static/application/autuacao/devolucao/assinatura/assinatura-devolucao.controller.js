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
	
	angular.autuacao.controller('AssinaturaDevolucaoController', function($scope, $stateParams, PeticaoService, PecaService, SignatureService, messages) {
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
		
		var peticoesAssinadas = [];
		
		var signingManager = SignatureService.signingManager();
		
		angular.forEach(idsPeticoes, function(id) {
			PeticaoService.consultar(id).then(function(peticao) {
				$scope.peticoes.push(peticao);
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
			angular.forEach($scope.peticoes, function(peticao) {
				var peca = $scope.pecaDocumentoDevolucao(peticao);
				var idDocumento = peca.documentoId;
				var signer = signingManager.createSigner();
				
				signer.onSignerReady(function(signerId) {
					signer.provideExistingDocument(idDocumento);
	            });
	            signer.onSigningCompleted(function(signedDocument) {
	            	signer.saveSignedDocument().then(function() {
	            		peticoesAssinadas.push({'peticaoId': peticao.id, 'documentoId': signedDocument});
	            	});
	            });
	            signer.onErrorCallback(function(error) {
	            	console.log('controller-error-callback');
	            	console.log(error);
	            });
	            signer.start();
			});
		};
		
		$scope.finalizar = function() {
			if (peticoesAssinadas.length == 0) {
				messages.error('É necessário assinar os documentos antes de finalizar.');
			}
		};
		
	});
	
})();

