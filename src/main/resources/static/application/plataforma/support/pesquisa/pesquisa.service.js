/**
 * Fornece serviços para realizar uma pesquisa simples, paginada ou por sugestão (autocomplete)
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('PesquisaService', ['$q', '$http', '$window', 'properties', function($q, $http, $window, properties) {
		
		//public methods
		
		/**
		 * Realiza uma pesquisa com uma lista de resultados no formato:
		 * [{id: 12, tipo: Processo, objeto: {...}}, {...}]
		 * @param command
		 * @return os resultados da pesquisa
		 */
		this.pesquisar = function(command) {
			return $http.post(properties.apiUrl + /*'/' + context + */'/pesquisas', command);
		};
		
		/**
		 * Realiza uma pesquisa com uma lista de resultados paginados no formato: 
		 * {content : [{id: 12, tipo: Processo, objeto: {...}}], page : {size:15, number:0, totalPages:1, totalElements:15}}
		 */
		this.pesquisarPaginado = function(command) {
			return $http.post(properties.apiUrl + /*'/' + context + */'/pesquisas/paginadas', command);
		};
		
		/**
		 * Realiza uma pesquisa por sugestões (autocomplete) com uma lista de resultados no formato: 
		 * [{id: 12, tipo: Processo, objeto: {...}}, {...}]
		 */
		this.pesquisarSugestao = function(command) {
			return $http.post(properties.apiUrl + /*'/' + context + */'/pesquisas/sugestoes', command);
		};
		
		/**
		 * Salva uma pesquisa configurada pelo usuário
		 */
		this.salvar = function(pesquisa) {
			if (!angular.isObject(pesquisa)) {
				throw "A pesquisa deve ser um objeto!";
			}
			var command = {indice:'pesquisa', tipo:'Pesquisa', objeto: pesquisa};
			return $http.post(properties.apiUrl + /*'/' + context + */'/indices/documentos', command);
		};
		
	}]);
})();
