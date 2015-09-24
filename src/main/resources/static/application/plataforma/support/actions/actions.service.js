/**
 * Fornece serviços para carregar as ações de um contexto, verificar permissões e
 * executar uma ação específica
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('ActionService', ['$q', '$http', '$templateCache', '$window', 'properties', function($q, $http, $templateCache, $window, properties) {

		var ARRAY_EXCEPTION = "Os recursos devem estar em um array!";
		
		//attributes
		var actions = {};
		
		//public methods
		
		/**
		 * Recupera uma ação
		 */
		this.get = function(id) {
			return angular.copy(actions[id]);
		};
		
		/**
		 * Carrega as ações de um determinado contexto
		 */
		this.load =	function(context) {
			return $q(function(resolve, reject) {
				$http.get(properties.apiUrl + /*'/' + context + */'/actions', $templateCache)
					.then(function(result) {
						angular.forEach(result.data, function(action) {
							action.context = context;
							actions[action.id] = action;
						});
						resolve();
					}, function(err) {
						reject(err);
					});
			});
		};
		
		/**
		 * Lista as ações a partir dos recursos e tipo dos recursos
		 */
		this.list = function(resources, type, contextFilter) {
			return $q(function(resolve, reject) {
				var allowedActions = [];
				var idsByContext = {};
				var context = '';
				
				//carrega as ações permitidas localmente e carrega em outro array
				//as ações para serem verificados no servidor
				angular.forEach(actions, function(action, id) {
					if (action.resourcesType === type && isAllowed(action, resources, contextFilter)) {
						// se possuir handlers devem ser verificadas
						if (action.hasConditionHandlers) {
							if (context === action.context) {
								idsByContext[context].push(id);
							} else {
								context = action.context;
								if (angular.isUndefined(idsByContext[context])) {
									idsByContext[context] = [id];
								}
							}
						} else {
							// carrega as já permitidas
							allowedActions.push(action);
						}
					}
				});
				// se não existir ações para serem verificadas, retorna as carregadas
				if (idsByContext.length === 0) {
					resolve(allowedActions);
				} else {
					var requests = [];
					// carrega todos as requisições que devem ser realizadas para
					// verificar as ações que ainda não foram permitidas
					angular.forEach(idsByContext, function(ids, context) {
						var data = { 'ids' : ids, 'resources' : resources };
						var request = $http.post(properties.apiUrl + /*'/' + context + */'/actions/isallowed', data);
						requests.push(request);
					});
					// espera todas as requisições terminarem para retornar as ações
					$q.all(requests)
						.then(function(results) {
							angular.forEach(results, function(result) {
								angular.forEach(result.data, function(id) {
									allowedActions.push(actions[id]);
								});
							});
							resolve(allowedActions);
						}, function (err) {
							reject(err);
						});
				}
			});
		};
		
		/**
		 * Verifica se uma ação específica pode ser executada com os recursos informados 
		 */
		this.isAllowed = function(id, resources) {
			return $q(function(resolve, reject) {
				var action = actions[id];
				
				// verifica primeiro as permissões localmente
				if (angular.isObject(action) && isAllowed(action, resources, action.context)) {
					// se não possui handlers permite
					if (!action.hasConditionHandlers) {
						resolve(true);
					} else {
						var success = function(result) {
							resolve(result.data);
						};
						var error = function(err) {
							reject(err);
						};
						// verifica os handlers no servidor 
						var request = null;
						if (angular.isArray(resources) && resources.length > 0) {
							var data = { 'resources' : resources };
							$http.post(properties.apiUrl + /*'/' + action.context + */'/actions/' + id + '/isallowed', data)
								.then(success, error);
						} else {
							$http.get(properties.apiUrl + /*'/' + context +*/ '/actions/' + id + '/isallowed')
								.then(success, error);
						}
					}
				} else {
					resolve(false);
				}
			});
		};
		
		/**
		 * Executa uma ação sobre os recursos informados
		 */
		this.execute = function(id, resources) {
			
			var context = actions[id].context;
			if (angular.isArray(resources)) {
				var data = { 'resources' : resources };
				return $http.post(properties.apiUrl + /*'/' + context +*/ '/actions/' + id + '/execute', data);
			} else if (isResourcesNullOrUndefined(resources)){
				return $http.get(properties.apiUrl + /*'/' + context +*/ '/actions/' + id + '/execute');
			}
			return $q.defer().reject(ARRAY_EXCEPTION);
		};
		
		/**
		 * Valida se o modo corresponde à quantidade de recursos
		 */
		this.isValidResources = function(action, resources) {
			var mode = action.resourcesMode;
			if (isResourcesNullOrUndefined(resources)) {
				return mode === "None";
			} else if (angular.isArray(resources)){
				if (resources.length === 0) {
					return mode === "None";
				} else if (resources.length === 1) {
					return mode === "One" || mode === "Many";
				} else {
					return mode === "Many";
				}
			}
			return false;
		};
		
		//private methods
		
		var isValidResources = this.isValidResources;
		
		/**
		 * Verifica se os recursos são nulos ou indefinidos  
		 */
		var isResourcesNullOrUndefined = function(resources) {
			if (resources === null || angular.isUndefined(resources)) {
				return true;
			}
			return false;
		};
		
		/**
		 * Valida se o usuário possui as permissões necessárias para listar uma ação
		 */
		//TODO Lucas.Rodrigues Recuperar os perfis de segurança do usuário
		var hasNeededAuthorities = function(action) {
			if (action.neededAuthorities.length === 0) {
				return true;
			}
			var role = JSON.parse($window.sessionStorage.getItem('papel'));
			return action.neededAuthorities.indexOf(role.nome) != -1;
		};
		
		/**
		 * Valida caso um contexto informado se a ação pertence a esse contexto
		 */
		var isActionContext = function(action, context) {
			return (!angular.isString(context) || context === action.context);
		};
		
		/**
		 * Valida se é permitido listar uma ação
		 */
		var isAllowed = function(action, resources, context) {
			if (!isActionContext(action, context) ||
					!isValidResources(action, resources) ||
					!hasNeededAuthorities(action)) {
				return false;
			}
			return true;
		};
	}]);
})();
