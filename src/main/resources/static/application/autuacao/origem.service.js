/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M3
 * @since 15.10.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('OrigemService', function($http, $q, properties) {
		return {
			listarTribunalJuizo : function(idProcedencia) {
				return $http.get(properties.apiUrl + '/tribunais-juizos/origens/' + idProcedencia).then(function(response) {
					return response.data;
				});
			},
			listarUnidadesFederacao : function() {
				return $http.get(properties.apiUrl + '/unidades-federacao').then(function(response) {
					return response.data;
				});
			}
		};
	});
	
})();