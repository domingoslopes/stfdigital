/**
 * @author Anderson.Araujo
 * 
 * @since 28.03.2016
 */
(function() {
	'use strict';

	angular.plataforma.factory('AssuntoService', function ($http, $q, properties) {
		return {
			listar : function(parametro) {
				var params = { termo:parametro };
				return $http.get(properties.apiUrl + '/assuntos', {params:params});
			}
		};
	});
})();