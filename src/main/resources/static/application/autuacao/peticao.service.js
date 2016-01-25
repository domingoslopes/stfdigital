/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('PeticaoService', function($http, $q, properties, ActionService) {
		return {
			consultar : function(peticaoId) {
				return $http.get(properties.apiUrl + '/peticoes/' + peticaoId).then(function(response) {
					return response.data;
				});
			},
			
			consultarVarias : function(peticaoIds) {
				var params = { ids : peticaoIds };
				return $http.get(properties.apiUrl + '/peticoes', { params : params }).then(function(response) {
					return response.data;
				});
			},
			
			assinarDevolucao : function(assinarDevolucaoCommands) {
				return ActionService.execute('assinar-devolucao-peticao', assinarDevolucaoCommands);
			},
			
			listarTipoPecas : function() {
				return $q(function(resolve, reject) {
					var tipoPecas = [{id : 1, descricao : "Petição Inicial"}, {id : 2 , descricao: "Ato coator"}, {id: 3, descricao: "Documentos Comprobatórios"}];
					resolve(tipoPecas);
				});
			},
			
			consultarPartes : function(idPeticao) {
				return $http.get(properties.apiUrl + '/peticoes/' + idPeticao +  '/partes');
			},
			
			listarStatus : function() {
				return $http.get(properties.apiUrl + '/peticoes/status/');
			},
			
			consultarProcessoWorkflow : function(idPeticao) {
				return $http.get(properties.apiUrl + '/peticoes/' + idPeticao +  '/processo-workflow').then(function(response) {
					return response.data;
				});
			},
			
			templateDevolucao : function(tipoDevolucao) {
				var params = { tipo : tipoDevolucao };
				return $http.get(properties.apiUrl + '/peticoes/template-devolucao', { params : params }).then(function(response) {
					return response.data;
				});
			},
			
			urlTemplateDevolucao : function(tipoDevolucao, extensao) {
				return properties.apiUrl + '/peticoes/template-devolucao?tipo=' + tipoDevolucao + '&ext=' + extensao;
			}
		};
	});
	
})();
