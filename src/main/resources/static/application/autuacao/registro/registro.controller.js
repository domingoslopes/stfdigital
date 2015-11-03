/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('RegistroPeticaoFisicaController', function ($log, $http, $state, $timeout, messages, properties, ActionService) {
		
		var registro = this;
		registro.tipoRecebimentos = [];
		registro.tipoRecebimento = '';
		registro.qtdApensos = "";
		registro.qtdVolumes = "";
		registro.numSedex = "";
		registro.recursos = [{}];
		
		registro.tipoRecebimentos = [{ id : 'BALCAO', nome : "Balcão" }, { id : 'SEDEX', nome : "Sedex" }, { id : 'MALOTE', nome : "Malote" }, { id : 'FAX', nome : "Fax" }, { id : 'EMAIL', nome : "E-mail" }];
		
		registro.validar = function() {
			var errors = null;
			if (registro.qtdVolumes.length === 0) {
				errors = "Você precisa informar o número de <b>volumes</b><br/>";
			}
			if (registro.qtdApensos.length === 0) {
				errors += "Você precisa informar o número de <b>apensos</b><br/>";
			}
			if (registro.tipoRecebimento.length === 0) {
				errors += "Você precisa selecionar uma <b>forma de envio</b>";
			}
			if (errors) {
				messages.error(errors);
				return false;
			}
			registro.recursos[0] = new RegistrarCommand(registro.qtdVolumes, registro.qtdApensos, registro.tipoRecebimento, registro.numSedex);
			return true;
		};
		
		registro.completar = function(data) {
			$state.go('dashboard');
			messages.success('Petição Física <b>#' + data + '</b> registrada com sucesso.');
		};
		
		function RegistrarCommand (quantidadeVolumes, quantidadeApensos, formaRecebimento, numeroSedex){
			var dto = {};

			dto.quantidadeVolumes = quantidadeVolumes;
			dto.quantidadeApensos = quantidadeApensos;
			dto.formaRecebimento = formaRecebimento;
			dto.numeroSedex = numeroSedex;
			
			return dto;
		}
	});

})();

