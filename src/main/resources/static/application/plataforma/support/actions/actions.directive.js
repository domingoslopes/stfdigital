/**
 * Diretiva que cria um botão com dropdown da lista de ações pertinentes aos
 * recursos, ou cria um botão específico para uma única ação
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	/**
	 * Cofigura um estado abstrato para registrar a url padrão para os
	 * estados de execução da ação.
	 * Os estados das ações devem ser configurados nos módulos:
	 * 
	 * $stateProvider.state('actions.contexto.acao', {
	 * 	// Configurar a view e controller de acordo com a especificação do ui-router
	 *  // A ação deve possuir o mesmo identificador definido no módulo do servidor
	 *  // O controlador pode acessar os recursos via $stateParams.resources
	 *	});
	 */
	angular.plataforma.config(['$stateProvider', function($stateProvider) {
		
		$stateProvider.state('actions', {
			abstract : true,
			params : {
				action : {},
				resources : []
			},
			views: {
				'modal': {
					templateUrl: 'application/plataforma/support/actions/modal.tpl.html',
					controller: 'ModalActionController'
				}
			}
		});
		
	}]);
	
	/**
	 * Diretiva de lista de ações
	 * Ex. de uso: 
	 * <actions resources="recursos" type="Long"
	 *  context="autuacao" btn-class="btn-default" /> 
	 */
	angular.plataforma.directive('actions', ['$state', 'ActionService', function ($state, ActionService) {
		return {
			restrict : 'AE',
			scope : {
				resources : '=', //obrigatório, recursos que sofrerão a ação
				type : '@', //obrigatório, indica o tipo dos recursos
				context : '@', //opcional, filtra ações de um determinado contexto
				btnClass: '@' //opcional, classes do botão, default= 'btn-default'
			},
			templateUrl : 'application/plataforma/support/actions/actions.tpl.html',
			controller : function($scope) {
				$scope.actions = [];
				$scope.btn = angular.isString($scope.btnClass) ? $scope.btnClass : "btn btn-default";
				
				var listActions = function() {
					//serviço que lista as ações
					ActionService.list($scope.resources, $scope.type, $scope.context)
					.then(function(actions) {
						$scope.actions = actions;
					});
				};
				
				//as ações devem ser recarregadas sempre que os recursos mudarem
				$scope.$watchCollection('resources', listActions);
				
				//vai para o estado de uma ação selecionada, passando os recursos como parâmetro
				$scope.go = function(action) {
					var params = {
						action : action,
						resources : $scope.resources
					};
					$state.go('actions.' + action.context + '.' + action.id, params);
				};
			}
		};
	}]);
	
	/**
	 * Botão de uma ação específica
	 * Ex. de uso: 
	 * <action id="excluir_recurso" resources="recursos"
	 * 	btn-class="btn-success"	icon-class="fa fa-hand-peace-o"
	 * 	show-description="false" show-not-allowed="false" /> 
	 */
	angular.plataforma.directive('action', ['$state', 'ActionService', function ($state, ActionService) {
		return {
			restrict : 'AE',
			scope : {
				id : '@', //obrigatório, identificador da ação
				resources : '=', //obrigatório, recursos que sofrerão a ação
				btnClass : '@', //opcional, classes do botão, default= 'btn btn-default'
				iconClass : '@', //opcional, classes do ícone
				showDescription : '=', //opcional, indica se deve aparecer a descrição, default= true
				showNotAllowed : '=' //opcional, indica se deve aparecer o botão mesmo não permitido,default= true
			},
			templateUrl : 'application/plataforma/support/actions/action.tpl.html',
			controller : function($scope) {
				ActionService.get($scope.id).then(function(action) {
					$scope.description = action.description;
					$scope.disabled = true;
					$scope.showAction = true;
					$scope.showIcon = angular.isString($scope.iconClass);
					$scope.btn = angular.isString($scope.btnClass) ? $scope.btnClass : "btn btn-default";
					$scope.icon = $scope.showIcon ? $scope.iconClass : "";
					
					if (angular.isUndefined($scope.showDescription) || !$scope.showIcon) {
						$scope.showDescription = true;
					}
					//Verifica se a ação é permitida
					ActionService.isAllowed($scope.id, $scope.resources)
						.then(function(isAllowed) {
							$scope.disabled = !isAllowed;
	
							if ($scope.disabled && angular.isDefined($scope.showNotAllowed)) {
								$scope.showAction = $scope.showNotAllowed;
							}
						});
					
					//vai para o estado de uma ação, passando os recursos como parâmetro
					$scope.go = function() {
						var params = {
							action : action,
							resources : $scope.resources
						};
						$state.go('actions.' + action.context + '.' + action.id, params);
					};
				});
			}
		};
	}]);
	
	/**
	 * Botão de uma ação específica no menu
	 * Ex. de uso: 
	 * <action-menu id="excluir_recurso" resources="recursos" /> 
	 */
	angular.plataforma.directive('actionMenu', ['$state', 'ActionService', function ($state, ActionService) {
		return {
			restrict : 'AE',
			transclude : true,
			replace : false,
			scope : {
				id : '@', //obrigatório, identificador da ação
				resources : '=', //obrigatório, recursos que sofrerão a ação
				state : '@', //obrigatório, estado para realizar a ação
				title : '@', //obrigatório, título para o menu
				details : '@' //opcional, detalhamento do item do menu, default= descrição da ação
			},
			templateUrl : 'application/plataforma/support/actions/actionmenu.tpl.html',
			controller : function($scope) {
				ActionService.get($scope.id).then(function(action) {
					$scope.showAction = false;
					
					if (angular.isUndefined($scope.details)) {
						$scope.details = action.description;
					}
					
					//Verifica se a ação é permitida
					ActionService.isAllowed($scope.id, $scope.resources)
						.then(function(isAllowed) {
							$scope.showAction = isAllowed;
						});
					
					//vai para o estado de uma ação, passando os recursos como parâmetro
					$scope.go = function() {
						var params = {
							action : action,
							resources : $scope.resources
						};
						$state.go($scope.state, params);
					};
				});
			}			
		};
	}]);

	/**
	 * Botão de uma ação específica
	 * Ex. de uso: 
	 * <action-executor id="excluir_recurso" resources="recursos"
	 * 	btn-class="btn-success"	icon-class="fa fa-hand-peace-o" description="Finalizar"
	 * 	show-description="true" verify-if-allowed="true" show-not-allowed="false" /> 
	 */
	angular.plataforma.directive('actionExecutor', ['ActionService', 'messages', function (ActionService, messages) {
		return {
			restrict : 'AE',
			scope : {
				id : '@', //obrigatório, identificador da ação
				resources : '=', //opcional, recursos que sofrerão a ação devem ser informados se a ação exigir
				result : '=', //opcional, resultado da execução da ação
				fnValidate : '&', //opcional, uma função para ser executada antes de enviar a requisição
				fnResult : '&', //opcional, uma função para ser executada após receber o resultado, o resultado é passado como parâmetro
				btnClass : '@', //opcional, classes do botão, default= 'btn btn-default'
				iconClass : '@', //opcional, classes do ícone
				description : '@', //opcional, descrição do botão, se não informado usará a descrição da ação
				showDescription : '=', //opcional, indica se deve aparecer a descrição, default= true
				verifyIfAllowed: '=', //opcional, indica se deve verificar se a ação é permitida, default= true
				showNotAllowed : '=', //opcional, indica se deve aparecer o botão mesmo não permitido, default= true
			},
			templateUrl : 'application/plataforma/support/actions/executor.tpl.html',
			controller : function($scope) {
				ActionService.get($scope.id).then(function(action) {
					if (angular.isObject(action) &&
							ActionService.isValidResources(action, $scope.resources)) {
						$scope.description = angular.isString($scope.description) ? $scope.description : action.description;
						$scope.disabled = true;
						$scope.showAction = true;
						$scope.showIcon = angular.isString($scope.iconClass);
						$scope.btn = angular.isString($scope.btnClass) ? $scope.btnClass : "btn btn-default";
						$scope.icon = $scope.showIcon ? $scope.iconClass : "";
						
						if (angular.isUndefined($scope.verifyIfAllowed)) {
							$scope.verifyIfAllowed = true;	
						}
						
						if (angular.isUndefined($scope.showDescription) || !$scope.showIcon) {
							$scope.showDescription = true;
						}
						
						//Verifica se a ação é permitida, caso seja pedido para verificar
						if ($scope.verifyIfAllowed) {
							ActionService.isAllowed($scope.id, $scope.resources)
								.then(function(isAllowed) {
									$scope.disabled = !isAllowed;
		
									if ($scope.disabled && angular.isDefined($scope.showNotAllowed)) {
										$scope.showAction = $scope.showNotAllowed;
									}
								});
						} else {
							$scope.disabled = false;
						}
						
						// Executa a ação
						$scope.execute = function() {
							if (angular.isFunction($scope.fnValidate())) {
								if (!$scope.fnValidate()()) {
									return;
								}
							}
							ActionService.execute($scope.id, $scope.resources)
								.then(function(result) {
									if (angular.isDefined($scope.result)) {
										$scope.result = result.data;
									}
									// verifica se o callback é uma função e executa
									if (angular.isFunction($scope.fnResult())) {
										$scope.fnResult()(result.data);
									}
								}, function(err) {
									messages.error(err);
								});
						};
					} else {
						messages.error("A ação e os recursos para a ação devem ser válidos!");
					}
				});
			}
		};
	}]);
	
})();

