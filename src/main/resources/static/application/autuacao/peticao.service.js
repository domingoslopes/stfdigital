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
				return $http.post(properties.apiUrl + '/peticao', peticionarCommand);
			},
			
			consultarPeticaoEletronica : function(id) {
				return $http.get(properties.apiUrl + '/peticao/' + id);
			},
			
			consultarPeticaoFisica: function(id) {
				return $http.get(properties.apiUrl + '/peticao/fisica/' + id);
			},
			
			autuar : function(id, autuarCommand) {
				return $http.post(properties.apiUrl + '/peticao/' + id + '/autuacao', autuarCommand);
			},
			
			distribuir : function(id, relator) {
				return $http.post(properties.apiUrl + '/peticao/' + id + '/distribuicao', JSON.stringify(relator));
			},
			
			registrar : function(registrarCommand){
				return $http.post(properties.apiUrl + '/peticao/fisica', registrarCommand);
			},
			
			preautuar : function(id, classe){
				return $http.post(properties.apiUrl + '/peticao/' + id + '/preautuacao', JSON.stringify(classe));
			}
			
		};
	});
	
})();
