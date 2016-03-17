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
		
		devolucao.showEditor = false;
		
		PeticaoService.consultarProcessoWorkflow(devolucao.peticaoId).then(function(data) {
			devolucao.processoWorkflowId = data;
		});
		
		PeticaoService.consultarMotivoDevolucao().then(function(data){
			devolucao.motivosDevolucao = data;
		});
		
		devolucao.carregarModelos = function(){
			ModeloService.consultarModeloPelaDevoluccao(motivoDevolucao).then(function(modelos){
				devolucao.modelos = modelos;
			});
		};
		
/*		var recuperarIdModelo = function(tipoDevolucao) {
			for (var i in devolucao.tiposDevolucao) {
				if (devolucao.tiposDevolucao[i].id == tipoDevolucao) {
					return devolucao.tiposDevolucao[i].modelo;
				}
			}
		};*/
		

		$scope.$watch('devolucao.motivoDevolucao', function() {
			if (devolucao.motivoDevolucao != '') {
				ModeloService.consultar(recuperarIdModelo(devolucao.motivoDevolucao)).then(function(modelo) {
					devolucao.modelo = modelo;
					ModeloService.extrairTags(devolucao.modelo.documento).then(function(tags) {
						devolucao.tags = tags;
					});
				});
			}
		});
		
		devolucao.tagsCarregadas = function() {
			return devolucao.tags.length > 0;
		};
		
		devolucao.gerarTexto = function() {
			TextoService.gerarTextoComTags(new GerarTextoPeticaoCommand(devolucao.peticaoId, devolucao.modelo.id, devolucao.tags)).then(function(texto) {
				messages.success("Texto gerado com sucesso");
				OnlyofficeService.recuperarNumeroEdicao(texto.documentoId).then(function(edicao) {
					iniciarEditor('Documento de Devolução', texto.documentoId, edicao.numero);
					devolucao.showEditor = true;
				});
			}, function() {
				messages.error("Erro ao gerar o texto");
			});
		};
		
		var GerarTextoPeticaoCommand = function(peticaoId, modeloId, tags) {
			this.peticaoId = peticaoId;
			this.modeloId = modeloId;
			this.substituicoes = tags;
		};
		
		var iniciarEditor = function(nome, documentoId, numeroEdicao) {
			$q.all([OnlyofficeService.criarUrlConteudoDocumento(documentoId),
				OnlyofficeService.recuperarUrlCallback(documentoId)])
				.then(function(urls) {
					devolucao.config = {
						editorConfig : {
							lang: 'pt-BR',
							customization: {
					            about: true,
					            chat: true
							},
							user: {
					            id: SecurityService.user().login,
					            name: SecurityService.user().nome
					        },
						},
						document: {
							src: urls[0],
							key: numeroEdicao,
							name: 'Texto: ' + nome,
							callbackUrl: urls[1]
						}
					};
				});
		};

		devolucao.save = function() {
			console.log('salvando conteúdo');
		};
		
		devolucao.validar = function() {
			var errors = null;
			if (devolucao.motivoDevolucao.length === 0) {
				errors = 'Você precisa selecionar <b>o motivo da devolução</b>.<br/>';
			}
			
			if (!angular.isNumber(devolucao.numeroOficio)) {
				errors += 'Você precisa informar <b>o número do ofício</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			devolucao.recursos.push(new DevolverCommand(devolucao.peticaoId, devolucao.motivoDevolucao, devolucao.numeroOficio));
			return true;
		};
		
		devolucao.completar = function() {
			$state.go('visualizar.peticao', {peticaoId: devolucao.peticaoId});
			messages.success('Petição devolvida com sucesso.');
		};
		
		function DevolverCommand(peticaoId, motivoDevolucao, numeroOficio, documento) {
			var command = {};
			command.peticaoId = peticaoId;
			command.motivoDevolucao = motivoDevolucao; 
			command.numeroOficio = numeroOficio;
			return command;
		}
	});
})();
