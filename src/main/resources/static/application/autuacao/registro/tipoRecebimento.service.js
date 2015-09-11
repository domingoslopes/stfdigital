/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.09.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('TipoRecebimentoService', function($http, properties) {
		return {
			listar : function() {
				return $http.get(properties.apiUrl + '/tiporecebimentos');
			}
		};
	});
	
})();