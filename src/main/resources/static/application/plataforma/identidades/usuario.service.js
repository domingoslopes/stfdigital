/**
 * Serviço de recuperação de informações de usuário.
 * 
 * @author Anderson.Araujo
 * 
 */
(function() {
	'use strict';

	angular.plataforma.service('UsuarioService', function($http, properties) {
		return {
			
			/**
			 * Recupera as informações do usuário logado.
			 */
			recuperarInformacoes : function() {
				return $http.get(properties.apiUrl + '/acessos/usuario').then(function(response) {
					return response.data;
				});
			}
		};
	});
})();
