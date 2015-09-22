/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('RegistroPeticaoFisicaController', function ($log, $http, $state, $timeout, messages, properties, TipoRecebimentoService, PeticaoService) {
		
		var registro = this;
		registro.tipoRecebimentos = [];
		registro.tipoRecebimento = '';
		registro.qtdApensos = "";
		registro.qtdVolumes = "";
		registro.numSedex = "";
		
		TipoRecebimentoService.listar().success(function(formas){
			registro.tipoRecebimentos = formas;
		});
		
		registro.completar = function() {
			
			if (registro.qtdApensos.length == 0){
				messages.error("Você precisa informar o número de <b>apensos</b>");
				return;
			}
			
			if (registro.qtdVolumes.length == 0){
				messages.error("Você precisa informar o número de <b>volumes</b>");
				return;
			}
			
			if (registro.tipoRecebimento.length == 0){
				messages.error("Você precisa selecionar uma forma de envio");
				return;
			}
			var command = new RegistrarCommand(registro.qtdVolumes, registro.qtdApensos, registro.tipoRecebimento, registro.numSedex);
			
			PeticaoService.registrar(command).success(function(data) {
				$state.go('dashboard');
				//messages.success('Petição Física <b>#2</b> registrada com sucesso.');
				}).error(function(data, status) {
					if (status === 400) {
						messages.error('A Petição Física <b>não pode ser registrada</b> porque ela não está válida.');
					}
			});
		};
		
		function RegistrarCommand (qtdVolumes, qtdApensos, tipoRecebimento, numSedex){
			var dto = {};
			dto.qtdVolumes = qtdVolumes;
			dto.qtdApensos = qtdApensos;
			dto.tipoRecebimento = tipoRecebimento;
			dto.numSedex = numSedex;
			return dto;
			
		}
	});

})();

