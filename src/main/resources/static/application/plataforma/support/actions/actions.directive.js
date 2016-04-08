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
		
		$stateProvider.state('action', {
			abstract : true,
			parent: 'root',
			params : {
				action : {},
				resources : []
			}, 
			views: {
				'modal@root': {
					templateUrl: 'application/plataforma/support/actions/modal.tpl.html',
					controller: 'ModalActionController'
				}
			}
		});
		
	}]);
	
	/**
	 * Diretiva de lista de ações
	 * Ex. de uso: 
	 * <actions resources="recursos" groups="peticao"
	 *  context="autuacao" btn-class="btn-default" /> 
	 */
	angular.plataforma.directive('actions', ['$state', 'ActionService', function ($state, ActionService) {
		return {
			restrict : 'AE',
			scope : {
				resources : '=', //obrigatório, recursos que sofrerão a ação
				groups : '@', //obrigatório, indica os grupos das ações, pode ser um array 
				context : '@', //opcional, filtra ações de um determinado contexto
				btnClass: '@', //opcional, classes do botão, default= 'btn-default'
				size: '@' //opcional, tamanho do icone fa-cog	
			},
			templateUrl : 'application/plataforma/support/actions/actions.tpl.html',
			controller : function($scope) {
				$scope.actions = [];
				$scope.btn = angular.isString($scope.btnClass) ? $scope.btnClass : "btn btn-default";
				$scope.sizeClass = angular.isString($scope.size) ? 'fa-'+ $scope.size + 'x'  : "";
				
				var listActions = function() {
					//serviço que lista as ações
					ActionService.list($scope.resources, $scope.groups, $scope.context).then(function(actions) {
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
					$state.go(action.id, params);
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
	angular.plataforma.directive('action', ['$state', '$q', 'ActionService', function ($state, $q, ActionService) {
		return {
			restrict : 'AE',
			scope : {
				id : '@', //obrigatório, identificador da ação
				resources : '=', //obrigatório, recursos que sofrerão a ação
				btnClass : '@', //opcional, classes do botão, default= 'btn btn-default'
				iconClass : '@', //opcional, classes do ícone
				description : '@', //opcional, descrição do botão, se não informado usará a descrição da ação
				showDescription : '=', //opcional, indica se deve aparecer a descrição, default= true
				showNotAllowed : '=' //opcional, indica se deve aparecer o botão mesmo não permitido,default= true
			},
			templateUrl : 'application/plataforma/support/actions/action.tpl.html',
			controller : function($scope) {
				
				var action = null;
				$scope.disabled = true;
				$scope.showAction = false;
				$scope.showIcon = angular.isString($scope.iconClass);
				$scope.btn = angular.isString($scope.btnClass) ? $scope.btnClass : "btn btn-default";
				$scope.icon = $scope.showIcon ? $scope.iconClass : "";
				
				if (angular.isUndefined($scope.showDescription) || !$scope.showIcon) {
					$scope.showDescription = true;
				}
				
				ActionService.get($scope.id).then(function(theAction) {
					
					action = theAction;
					$scope.description = angular.isString($scope.description) ? $scope.description : action.description;
					
					$q.when($scope.resources, function(resources) {
						$scope.resources = resources;
						//Define uma função que verifica se a ação é permitida
						var isAllowed = function() {
							ActionService.isAllowed($scope.id, resources)
								.then(function(isAllowed) {
									$scope.disabled = !isAllowed;
		
									if ($scope.disabled && angular.isDefined($scope.showNotAllowed)) {
										$scope.showAction = $scope.showNotAllowed;
									} else {
										$scope.showAction = true;
									}
								});
						};
						
						$scope.$watchCollection('resources', isAllowed);
					});
				});
				
				//vai para o estado de uma ação, passando os recursos como parâmetro
				$scope.go = function() {
					var params = {
						action : action,
						resources : $scope.resources
					};
					$state.go(action.id, params);
				};
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
			restrict : 'A',
			scope : { },
			templateUrl : 'application/plataforma/support/actions/actionmenu.tpl.html',
			controller : function($scope) {
				
				var resources = [];
				var group = 'menu';
				$scope.actions = [];
				
				ActionService.list(resources, group).then(function(actions) {
					$scope.actions = actions;
				});
				
				//vai para o estado de uma ação, passando os recursos como parâmetro
				$scope.go = function(action) {
					var params = {
						action : action
					};
					$state.go(action.id, params);
				};
			}			
		};
	}]);

	/**
	 * Botão de uma ação específica
	 * Ex. de uso: 
	 * <action-executor id="excluir_recurso" resources="recursos"
	 * 	btn-class="btn-success"	icon-class="fa fa-hand-peace-o" description="Finalizar"
	 * 	show-description="true" enable="true" /> 
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
				fnError : '&?', //opcional, uma função para ser executada após ocorrer um erro.
				btnClass : '@', //opcional, classes do botão, default= 'btn btn-default'
				iconClass : '@', //opcional, classes do ícone
				description : '@', //opcional, descrição do botão, se não informado usará a descrição da ação
				showDescription : '=', //opcional, indica se deve aparecer a descrição, default= true
				enable : '=' //opcional, indica se a ação deve estar habilitada, default = true
			},
			templateUrl : 'application/plataforma/support/actions/executor.tpl.html',
			controller : function($scope) {
				
				$scope.disabled = true;
				$scope.showAction = false;
				$scope.showIcon = angular.isString($scope.iconClass);
				$scope.btn = angular.isString($scope.btnClass) ? $scope.btnClass : "btn btn-default";
				$scope.icon = $scope.showIcon ? $scope.iconClass : "";
				
				ActionService.get($scope.id).then(function(action) {
					
					$scope.description = angular.isString($scope.description) ? $scope.description : action.description;
							
					if (angular.isUndefined($scope.showDescription) || !$scope.showIcon) {
						$scope.showDescription = true;
					}
					$scope.showAction = true;
					
					$scope.$watch('enable', function() {
						$scope.disabled = angular.isDefined($scope.enable) ? !$scope.enable : false;
					});
					
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
								if (angular.isDefined($scope.fnError)) {
									$scope.fnError({'error': err.data});
								} else {
									messages.error(err);
								}
							});
					};
				});
			}
		};
	}]);
	
})();

