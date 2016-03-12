/**
 * Controlador da tela de criação de modelo.
 * 
 * @author Tomas.Godoi
 * @since 1.0.0
 * @since 01.03.2016
 */
(function() {
	'use strict';

	angular.plataforma.controller('EdicaoConteudoModeloController', function(TipoModeloService, ModeloService, OnlyofficeService, $state, $stateParams, $q, SecurityService) {
		var self = this;
		
		self.modelo = {};
		self.tiposModelo = [];
		
		TipoModeloService.listar().then(function(tiposModelo) {
			self.tiposModelo = tiposModelo;
		});
		
		
		ModeloService.consultar($stateParams.idModelo).then(function(modelo) {
			self.modelo = modelo;
			OnlyofficeService.recuperarNumeroEdicao(self.modelo.documento).then(function(edicao) {
				iniciarEditor(edicao.numero);
			});
		});
		
		var iniciarEditor = function(numeroEdicao) {
			$q.all([OnlyofficeService.criarUrlConteudoDocumento(self.modelo.documento),
				OnlyofficeService.recuperarUrlCallback(self.modelo.documento)])
				.then(function(urls) {
					self.config = {
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
								name: 'Modelo: ' + self.modelo.tipoModelo.descricao + ' - ' + self.modelo.nome,
								callbackUrl: urls[1]
							}
						};
				});
		};

		self.save = function() {
			console.log('salvando conteúdo');
		};
		
		self.finalizar = function() {
			$state.go('dashboard');
		};
	});
})();