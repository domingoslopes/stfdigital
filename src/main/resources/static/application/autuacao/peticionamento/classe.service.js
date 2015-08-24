/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('ClasseService', function($http, properties) {
		return {
			listar : function() {
				return $http.get(properties.apiUrl + '/classes');
			}
		};
	});
	
})();
