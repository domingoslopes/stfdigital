/**
 * Service do Tipo de Modelo
 * 
 * @author Tomas.Godoi
 */
(function() {
	'use strict';
	
	angular.plataforma.factory('TipoModeloService', function($http, properties) {
		return {
			listar: function() {
				return $http.get(properties.apiUrl + '/tipos-modelo').then(function(response) {
					return response.data;
				});
			}
		}
	});
	
})();