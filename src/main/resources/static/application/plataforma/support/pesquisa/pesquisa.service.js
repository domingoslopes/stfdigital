/**
 * Fornece serviços para realizar uma pesquisa simples, paginada ou por sugestão (autocomplete)
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('PesquisaService', ['$q', '$http', 'properties', function($q, $http, properties) {
		
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
		 * Realiza uma pesquisa avançada de forma paginada 
		 * 
		 * @param command
		 * @return os resultados da pesquisa paginada
		 */
		this.pesquisarAvancado = function(command) {
			return $http.post(properties.apiUrl + /*'/' + context + */'/pesquisas/avancada', command);
		};
		
		/**
		 * Salva uma pesquisa configurada pelo usuário
		 */
		this.salvar = function(command) {
			return $http.post(properties.apiUrl + /*'/' + context + */'/pesquisas/avancadas', command);
		};
		
		/**
		 * Recupera as pesquisas salvas pelo usuário
		 */
		this.recuperarMinhasPesquisas = function() {
			return $http.get(properties.apiUrl + /*'/' + context + */'/pesquisas/avancadas');
		};
		
		/**
		 * Consulta uma pesquisa avançada salva
		 */
		this.consultar = function(pesquisaId) {
			if (!angular.isNumber(pesquisaId)) {
				throw "O id da pesquisa deve ser informado!";
			}
			return $http.get(properties.apiUrl + /*'/' + context + */'/pesquisas/avancadas/' + pesquisaId);
		};
		
		/**
		 * Recupera os critérios de pesquisas já no formato do angular-elastic-builder
		 */
		this.carregarCampos = function(campos, indices) {
			
			if (!angular.isArray(indices) || indices.length == 0) {
				throw "Especifique os índices para carregar os critérios de pesquisa!"; 
			}
			
			var deferred = $q.defer();
			var config = { params : { indices : indices } };
			
			$http.get(properties.apiUrl + /*'/' + context + */'/pesquisas/criterios', config)
				.then(function(result) {
					var criterios = result.data;
					angular.forEach(criterios, function(criterio) {
						campos[criterio.campo] = (montaCampo(criterio));
					});
					deferred.resolve();
				}, function() {
					deferred.reject();
				});
			return deferred.promise;
		};
		
		var montaCampo = function(criterio) {
			
			var campo = {};
			campo.description = criterio.descricao;
			
			if (criterio.tipo == "NUMERO") {
				campo.type = 'number';
			}
			
			if (criterio.tipo == "TEXTO") {
				campo.type = 'term';
			}
			
			if (criterio.tipo == "BOLEANO") {
				campo.type = 'boolean';
			}
			
			if (criterio.tipo == "DATA") {
				campo.type = 'date';
			}
			
			if (criterio.tipo == "SELECAO") {
				campo.type = 'multi';
				campo.choices = [];
				
				$http.get(properties.apiUrl + criterio.fonteDados.api).then(function(result) {
					if (angular.isArray(result.data)) { 
						angular.forEach(result.data, function(r) {
							var choice = {
								value : r[criterio.fonteDados.valor],
								description : r[criterio.fonteDados.descricao]
							};
							campo.choices.push(choice);
						});
					}
				});
			}
			return campo;
		};
		
	}]);
})();
