/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M3
 * @since 15.10.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('ProcessoService', function($http, $q, properties) {
		return {
			consultar : function(peticaoId) {
				return $http.get(properties.apiUrl + '/processos/' + peticaoId);
			},
			
			listarStatus : function() {
				return ["distribuido"];
			}
		};
	});
	
})();
