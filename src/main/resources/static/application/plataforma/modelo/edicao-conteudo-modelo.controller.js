/**
 * Controlador da tela de criação de modelo.
 * 
 * @author Tomas.Godoi
 * @since 1.0.0
 * @since 01.03.2016
 */
(function() {
	'use strict';

	angular.plataforma.controller('EdicaoConteudoModeloController', function(TipoModeloService, ModeloService, OnlyofficeService, $state, $stateParams, $q, SecurityService, messages) {
		var self = this;
		
		self.modelo = {};
		self.tiposModelo = [];
		self.editor = {};
		
		TipoModeloService.listar().then(function(tiposModelo) {
			self.tiposModelo = tiposModelo;
		});
		
		
		ModeloService.consultar($stateParams.idModelo).then(function(modelo) {
			self.modelo = modelo;
			self.documento = {
				id: self.modelo.documento,
				nome: 'Modelo: ' + self.modelo.tipoModelo.descricao + ' - ' + self.modelo.nome
			};
		});

		self.concluiuEdicao = function() {
			messages.success('Modelo editado com sucesso.');
			$state.go('dashboard');
		};
		
		self.timeoutEdicao = function() {
			messages.error('Não foi possível concluir a edição do modelo.');
		};
		
		self.finalizar = function() {
			self.editor.api.salvar();
		};
	});
})();