/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('RegistroPeticaoFisicaController', function ($log, $http, $state, messages, properties) {
		var registro = this;
		
		registro.tipoRecebimento = '';
		
		registro.completar = function() {
			$http.post(properties.apiUrl + '/peticao/fisica', {tipoRecebimento:registro.tipoRecebimento}).success(function(data) {
				$state.go('dashboard');
				messages.success('Petição Física <b>#0001</b> registrada com sucesso.');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição Física <b>não pode ser registrada</b> porque ela não está válida.');
				}
			});
		};
	});

})();

