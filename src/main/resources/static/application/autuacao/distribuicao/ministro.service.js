/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('MinistroService', function($http, properties) {
		return {
			listar : function() {
				return $http.get(properties.apiUrl + '/ministros');
			}
		};
	});
	
})();
