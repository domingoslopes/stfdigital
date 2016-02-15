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
			consultar : function(processoId) {
				return $http.get(properties.apiUrl + '/processos/' + processoId);
			},
			
			listarStatus : function() {
				return $http.get(properties.apiUrl + '/processos/status');
			},
			
			consultarMotivos : function(){
				return $http.get(properties.apiUrl + '/motivos');
			},
			
			consultarPorPeticao : function(peticaoId){
				return $http.get(properties.apiUrl + '/peticoes/' + peticaoId + '/processo');
			},
			
			consultarTeses : function(tipoTese, numeroTese) {
				return $http.get(properties.apiUrl + '/teses', {
					params: {tipo:tipoTese, numero:numeroTese}
				});
			}
		};
	});
	
})();
