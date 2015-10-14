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
		
		registro.tipoRecebimentos = [{ id : 'BALCAO', nome : "Balcão" }, { id : 'SEDEX', nome : "Sedex" }, { id : 'MALOTE', nome : "Malote" }, { id : 'FAX', nome : "Fax" }, { id : 'EMAIL', nome : "E-mail" }];
		
		registro.completar = function() {
			
			if (registro.qtdApensos.length === 0) {
				messages.error("Você precisa informar o número de <b>apensos</b>");
				return;
			}
			
			if (registro.qtdVolumes.length === 0) {
				messages.error("Você precisa informar o número de <b>volumes</b>");
				return;
			}
			
			if (registro.tipoRecebimento.length === 0) {
				messages.error("Você precisa selecionar uma forma de envio");
				return;
			}
			
			var command = new RegistrarCommand(registro.qtdVolumes, registro.qtdApensos, registro.tipoRecebimento, registro.numSedex);
			
			ActionService.execute('registrar_peticao_fisica', [command])
			  .success(function(data) {
				$state.go('dashboard');
				messages.success('Petição Física <b>#' + data + '</b> registrada com sucesso.');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição Física <b>não pode ser registrada</b> porque ela não está válida.');
				}
			});
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

