/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.10.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('OrgaoService', function($http, properties) {
		return {
			listar : function() {
				return $http.get(properties.apiUrl + '/orgaos');
			},
			
			listarRepresentados: function(verificarPerfil) {
				var params = { verificarPerfil : (verificarPerfil == true) ? true : false };
				return $http.get(properties.apiUrl + '/orgaos/representados', { params : params });
			},
			
			consultar : function(orgaoId) {
				return $http.get(properties.apiUrl + '/orgaos/' + orgaoId);
			}
		};
	});
	
})();