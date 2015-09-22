/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('PeticaoService', function($http, properties) {
		return {
			listar : function() {
				return $http.get(properties.apiUrl + '/peticoes');
			},
			
			peticionar : function(peticionarCommand) {
				return $http.post(properties.apiUrl + '/peticoes', peticionarCommand);
			},
			
			consultarPeticao : function(peticaoId) {
				return $http.get(properties.apiUrl + '/peticoes/' + peticaoId);
			},
			
			autuar : function(autuarCommand) {
				return $http.post(properties.apiUrl + '/peticoes/' + autuarCommand.peticaoId + '/autuar', autuarCommand);
			},
			
			distribuir : function(distribuirCommand) {
				return $http.post(properties.apiUrl + '/peticoes/' + distribuirCommand.peticaoId + '/distribuir', distribuirCommand);
			},
			
			devolver : function(devolverCommand) {
				return $http.post(properties.apiUrl + '/peticoes/' + devolverCommand.peticaoId + '/devolver', devolverCommand);
			},
			
			registrar : function(registrarCommand){
				return $http.post(properties.apiUrl + '/peticoes/fisicas', registrarCommand);
			},
			
			preautuar : function(preautuarCommand){
				return $http.post(properties.apiUrl + '/peticoes/fisicas/' + preautuarCommand.peticaoId + '/preautuar', preautuarCommand);
			}
			
		};
	});
	
})();
