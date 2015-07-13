/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('autuacao').controller('RegistroPeticaoFisicaController', function ($log, $http, $state) {
		var registro = this;
		
		registro.tipoRecebimento = '';
		
		registro.completar = function() {
			$http.post('/api/peticao/fisica', {tipoRecebimento:'1'}).success(function(data, status, headers, config) {
				$log.debug('Sucesso');
				$state.go('root.home');
			}).error(function(data, status, headers, config) {
				$log.debug('Erro');
			});
		};
	});

})();

