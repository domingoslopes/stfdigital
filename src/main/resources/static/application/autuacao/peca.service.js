/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('PecaService', function($http, $q, properties) {
		return {
			consultarDocumento : function(documentoId) {
				return $http.get(properties.apiUrl + '/documentos/' + documentoId).then(function(response) {
					return response.data;
				});
			}
			
		};
	});
	
})();
