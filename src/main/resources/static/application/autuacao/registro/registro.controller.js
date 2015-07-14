/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('autuacao').controller('RegistroPeticaoFisicaController', function ($log, $http, $state, messages) {
		var registro = this;
		
		registro.tipoRecebimento = '';
		
		registro.completar = function() {
			$http.post('/api/peticao/fisica', {tipoRecebimento:'1'}).success(function(data, status, headers, config) {
				$state.go('root.home');
				messages.success('Petição Física <b>#0001</b> registrada com sucesso.');
			}).error(function(data, status, headers, config) {
				messages.error('Petição Física <b>não pode ser registrada</b> porque ela já existe em nossa base de dados.');
			});
		};
	});

})();

