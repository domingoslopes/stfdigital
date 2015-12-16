/**
 * Service de acessos
 * 
 * @author Gabriel Teles
 * @since 16/12/2015
 */
(function() {
	'use strict';
	
	angular.plataforma.service('AcessosService', function(properties, $http) {
		/**
		 * Retorna todos os grupos do sistema
		 */
		this.grupos = function() {
			return $http.get(properties.apiUrl + '/acessos/grupos');
		}
		
		/**
		 * Retorna todos os papeis do sistema
		 */
		this.papeis = function() {
			return $http.get(properties.apiUrl + '/acessos/papeis');
		}
	});
})();