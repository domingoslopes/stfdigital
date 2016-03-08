/**
 * Controlador da tela de criação de modelo.
 * 
 * @author Tomas.Godoi
 * @since 1.0.0
 * @since 01.03.2016
 */
(function() {
	'use strict';

	angular.plataforma.controller('CriacaoModeloController', function(TipoModeloService, messages, $location) {
		var cmc = this;
		
		cmc.tiposModelo = [];
		TipoModeloService.listar().then(function(tiposModelo) {
			cmc.tiposModelo = tiposModelo;
		});

		cmc.modelo = {};
		cmc.recursos = [];
		
		cmc.validar = function() {
			var errors = "";
			if (!cmc.modelo.tipoModelo) {
				errors += "Você precisa informar o <b>Tipo do modelo</b><br/>";
			}
			if (!cmc.modelo.nome) {
				errors += "Você precisa informar o <b>Nome do modelo</b><br/>";
			}
			if (errors) {
				messages.error(errors);
				return false;
			}
			cmc.recursos[0] = new CriarModeloCommand(cmc.modelo.tipoModelo, cmc.modelo.nome);
			return true;
		};
		
		cmc.completar = function(idModelo) {
			messages.success('Modelo criado com sucesso.');
			$location.url('modelo/' + idModelo + '/editar');
		};
		
		function CriarModeloCommand(tipoModelo, nome) {
			var dto = {};

			dto.tipoModelo = tipoModelo;
			dto.nome = nome;
			
			return dto;
		}
		
	});
})();