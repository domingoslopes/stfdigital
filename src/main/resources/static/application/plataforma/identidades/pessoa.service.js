/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 19.01.2013
 */ 
(function() {
	'use strict';

	angular.plataforma.service('PessoaService', function(properties, $http) {

		return {
			pesquisar : function(pessoaId) {
				return $http.get(properties.apiUrl + '/pessoas/' + pessoaId);
			}
		};
	});
})();