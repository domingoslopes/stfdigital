/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DevolucaoController', function (PeticaoService, ModeloService, TextoService, OnlyofficeService, SecurityService, $state, $stateParams, $scope, $q, messages) {
		var devolucao = this;
		
		var resource = $stateParams.resources[0];
		devolucao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		devolucao.recursos = [];
		devolucao.modelos = [];
		devolucao.modeloId = '';
		devolucao.motivoDevolucao = '';
		devolucao.documento = '';
		devolucao.tags = [];
		
		devolucao.editor = {};
		var concluirEdicaoDeferred = null;
		
		devolucao.showEditor = false;
		
		PeticaoService.consultarProcessoWorkflow(devolucao.peticaoId).then(function(data) {
			devolucao.processoWorkflowId = data;
		});
		
		PeticaoService.consultarMotivoDevolucao().then(function(data){
			devolucao.motivosDevolucao = data.data;
		});
		
		$scope.$watch('devolucao.motivoDevolucao', function() {
			if (devolucao.motivoDevolucao != '') {
				ModeloService.consultarModelosPorMotivo(devolucao.motivoDevolucao).then(function(modelos){
					devolucao.modelos = modelos;
				});
			}
		});
		
		devolucao.extrairTags = function(){
			ModeloService.consultar(devolucao.modeloId).then(function(modelo) {
				devolucao.modelo = modelo;
				ModeloService.extrairTags(devolucao.modelo.documento).then(function(tags) {
					devolucao.tags = tags;
				});
			});
		}
		
		devolucao.tagsCarregadas = function() {
			return devolucao.tags.length > 0;
		};
		
		devolucao.gerarTexto = function() {
			TextoService.gerarTextoComTags(new GerarTextoPeticaoCommand(devolucao.peticaoId, devolucao.modelo.id, devolucao.tags)).then(function(texto) {
				messages.success("Texto gerado com sucesso");
				devolucao.documento = {
					id: texto.documentoId,
					nome: 'Documento de Devolução'
				};
				devolucao.showEditor = true;
			}, function() {
				messages.error("Erro ao gerar o texto");
			});
		};
		
		devolucao.concluiuEdicao = function() {
			concluirEdicaoDeferred.resolve();
		};
		
		devolucao.timeoutEdicao = function() {
			concluirEdicaoDeferred.reject();
			messages.error("Não foi possível concluir a edição do texto.");
		};
		
		var GerarTextoPeticaoCommand = function(peticaoId, modeloId, tags) {
			this.peticaoId = peticaoId;
			this.modeloId = modeloId;
			this.substituicoes = tags;
		};
		
		devolucao.validar = function() {
			concluirEdicaoDeferred = $q.defer();
			devolucao.editor.api.salvar();
			return concluirEdicaoDeferred.promise.then(function() {
				var errors = null;
				if (devolucao.motivoDevolucao.length === 0) {
					errors = 'Você precisa selecionar <b>o motivo da devolução</b>.<br/>';
				}
				
				if (errors) {
					messages.error(errors);
					return false;
				}
				devolucao.recursos.push(new DevolverCommand(devolucao.peticaoId, devolucao.modeloId, devolucao.motivoDevolucao));
				return true;
			}, function() {
				return false;
			});
		};
		
		devolucao.completar = function() {
			$state.go('visualizar.peticao', {peticaoId: devolucao.peticaoId});
			messages.success('Petição devolvida com sucesso.');
		};
		
		function DevolverCommand(peticaoId, modeloId , documento) {
			var command = {};
			command.peticaoId = peticaoId;
			command.modeloId = modeloId; 
			return command;
		}
	});
})();
