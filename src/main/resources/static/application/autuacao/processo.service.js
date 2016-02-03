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
			
			consultarRecursal: function(peticaoId) {
				return $http.get(properties.apiUrl + '/processos/recursais/' + peticaoId);
			},
			
			listarStatus : function() {
				return $http.get(properties.apiUrl + '/processos/status');
			},
			
			consultarMotivos : function(){
				return $http.get(properties.apiUrl + '/motivos');
			},
			
			consultarProcessoPeloIdPeticao : function(peticaoId){
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
