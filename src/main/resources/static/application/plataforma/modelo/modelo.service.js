/**
 * Service de Modelo.
 * 
 * @author Tomas.Godoi
 */
(function() {
	'use strict';
	
	angular.plataforma.factory('ModeloService', function($http, properties) {
		return {
			consultar: function(id) {
				return $http.get(properties.apiUrl + "/modelos/" + id).then(function(response) {
					return response.data;
				});
			},
			listar: function() {
				return $http.get(properties.apiUrl + "/modelos").then(function(response) {
					return response.data;
				});
			},
			extrairTags: function(id) {
				return $http.get(properties.apiUrl + "/documentos/" + id + "/tags").then(function(response) {
					return response.data;
				});
			}
		};
	});
})();