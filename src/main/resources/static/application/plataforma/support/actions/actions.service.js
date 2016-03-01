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

	angular.plataforma.service('ActionService', ['$q', '$http', '$templateCache', 'SecurityService', 'properties', function($q, $http, $templateCache, SecurityService, properties) {

		var ACTION_NOTLOADED_EXCEPTION = "A ação não foi carregada: ";
		var ARRAY_EXCEPTION = "Os recursos devem estar em um array!";
		
		//attributes
		var deferred = $q.defer();
		var that = this;
		
		//public methods
		
		/**
		 * Recupera uma ação
		 */
		this.get = function(id) {
			return $q.when(deferred.promise, function(actions) {
				if (angular.isUndefined(actions[id])) {
					return $q.reject();
				}
				return angular.copy(actions[id]);
			}, function() {
				return $q.reject();
			});
		};
		
		/**
		 * Carrega as ações de um determinado contexto
		 */
		this.load =	function(context) {
			return $http.get(properties.apiUrl + /*'/' + context + */'/actions', $templateCache)
					.then(function(result) {
						var actions = {};
						angular.forEach(result.data, function(action) {
							action.context = context;
							actions[action.id] = action;
						});
						deferred.resolve(actions);
					}, function(err) {
						deferred.reject(err);
					});
		};
		
		/**
		 * Lista as ações a partir dos recursos e grupo de ações
		 */
		this.list = function(resources, groups, contextFilter) {
			return $q(function(resolve, reject) {
				groups = angular.isArray(groups) ? groups : [groups];
				var allowedActions = [];
				var idsByContext = {};
				var context = '';
				
				return deferred.promise.then(function(actions) {
					//carrega as ações permitidas localmente e carrega em outro array
					//as ações para serem verificados no servidor
					angular.forEach(actions, function(action, id) {
						if (containsAny(action.groups, groups) && isAllowed(action, resources, contextFilter)) {
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
			});
		};
		
		/**
		 * Verifica se uma ação específica pode ser executada com os recursos informados 
		 */
		this.isAllowed = function(id, resources) {
			return $q(function(resolve, reject) {	
				return that.get(id)
					.then(function(action) {
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
					}, function(err) {
						reject(err);
					});
			});
		};
		
		/**
		 * Executa uma ação sobre os recursos informados
		 */
		this.execute = function(id, resources) {
			return that.get(id)
				.then(function(action) {
					var context = action.context;
					if (angular.isArray(resources)) {
						var data = { 'resources' : resources };
						return $http.post(properties.apiUrl + /*'/' + context +*/ '/actions/' + id + '/execute', data);
					} else if (isResourcesNullOrUndefined(resources)){
						return $http.get(properties.apiUrl + /*'/' + context +*/ '/actions/' + id + '/execute');
					}
					return $q.defer().reject(ARRAY_EXCEPTION);
				}, function(err) {
					return $q.defer().reject(err);
				});
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
					!that.isValidResources(action, resources)) {
				return false;
			}
			return true;
		};
		
		var containsAny = function(source, target) {
		    var result = source.filter(function(item){
		    	return target.indexOf(item) > -1
		    });
		    return (result.length > 0);  
		}
	}]);
})();
