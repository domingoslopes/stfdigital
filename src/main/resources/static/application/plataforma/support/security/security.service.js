/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.07.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('SecurityService', function($http, properties) {
		
		var user = null;
		
		var loadUser = function() {
			$.ajax({
				url: properties.apiUrl + '/acessos/usuario',
			    async: false,
			    success: function(data) {
			    	user = data;
			    }
			});
		};
		
		this.user = function() {
			if (user === null) {
				loadUser();
			}
			return user;
		};
		
		this.logout = function() {
			return $http.post('/logout');
		};
	});
	
})();
