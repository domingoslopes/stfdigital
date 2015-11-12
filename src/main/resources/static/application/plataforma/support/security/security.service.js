/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('SecurityService', function($http, properties) {
		return {
			papeis : function() {
				var resultado = [];
				$.ajax({
					url: properties.apiUrl + '/usuario',
				    async: false,
				    success: function(data) {
				    	resultado.push({nome : data.name});
				    }
				});
				return resultado;
			},
			logout : function() {
				return $http.post('/logout');
			}
		};
	});
	
})();
