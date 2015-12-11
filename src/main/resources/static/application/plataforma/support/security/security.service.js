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
		
		/**
		 * Verifica se o usuário logado possui um papel através do nome do papel
		 * 
		 * @param string Nome do papel
		 * @return boolean  
		 */
		this.hasPapel = function hasPapel(papelNome) {
			var localUser = this.user();
			
			if (localUser === null) {
				return false;
			}

			for (var i = 0; i < localUser.papeis.length; i++) {
				var papel = localUser.papeis[i];
				
				if (papel.nome === papelNome) {
					return true;
				}
			};
			
			return false;
		}
		
		this.logout = function() {
			return $http.post('/logout');
		};
	});
	
})();
