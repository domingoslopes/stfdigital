/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('PeticaoService', function($http, $q, properties) {
		return {
			peticionar : function(peticionarCommand) {
				return $http.post(properties.apiUrl + '/peticoes', peticionarCommand);
			},
			
			consultar : function(peticaoId) {
				return $http.get(properties.apiUrl + '/peticoes/' + peticaoId);
			},
			
			preautuar : function(peticaoId, preautuarCommand){
				return $http.post(properties.apiUrl + '/peticoes/fisicas/' + peticaoId + '/preautuar', preautuarCommand);
			},
			
			autuar : function(peticaoId, autuarCommand) {
				return $http.post(properties.apiUrl + '/peticoes/' + peticaoId + '/autuar', autuarCommand);
			},
			
			distribuir : function(peticaoId, distribuirCommand) {
				return $http.post(properties.apiUrl + '/peticoes/' + peticaoId + '/distribuir', distribuirCommand);
			},
			
			devolver : function(peticaoId, devolverCommand) {
				return $http.post(properties.apiUrl + '/peticoes/' + peticaoId + '/devolver', devolverCommand);
			},
			
			registrar : function(registrarCommand){
				return $http.post(properties.apiUrl + '/peticoes/fisicas', registrarCommand);
			},
			
			listarTipoPecas : function() {
				return $q(function(resolve, reject) {
					var tipoPecas = [{id : 1, descricao : "Petição Inicial"}, {id : 2 , descricao: "Ato coator"}, {id: 3, descricao: "Documentos Comprobatórios"}];
					resolve(tipoPecas);
				});
			}
		};
	});
	
})();
