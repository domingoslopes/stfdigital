/**
 * Diretiva que cria um botão com drildown da lista de ações pertinentes aos
 * recursos, ou cria um botão específico para uma única ação
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	/**
	 * Diretiva de lista de ações
	 * Ex. de uso: 
	 * <actions resources="recursos" type="Long" context="autuacao" />
	 * O atributo "context" é opcional para filtrar ações de um determinado contexto 
	 */
	angular.plataforma.directive('actions', ['$state', 'ActionService', function ($state, ActionService) {
		return {
			restrict : 'E',
			scope : {
				resources : '=',
				type : '@',
				context : '@'},
			template : '/application/plataforma/support/actions/actions.tpl.html',
			controller : function($scope) {
				$scope.actions = [];
				
				var listActions = function() {
					ActionService.list($scope.resources, $scope.type, $scope.context)
					.then(function(actions) {
						angular.copy(actions, $scope.actions);
					});
				};
				
				listActions();
				
				$scope.$watch('resources', listActions);
				
				$scope.go = function(action) {
					$state.go('actions.' + action.context + '.' + action.id);
				}
			}
		};
	}]);
	
	/**
	 * Botão de uma ação específica
	 * Ex. de uso: 
	 * <action id="excluir_recurso" resources="recursos" icon="fa fa-hand-peace-o" show-description="false" /> 
	 */
	angular.plataforma.directive('action', ['$state', 'ActionService', function ($state, ActionService) {
		return {
			restrict : 'E',
			scope : {
				id : '@',
				resources : '=',
				icon : '@',
				showDescription : '@'},
			template : '/application/plataforma/support/actions/action.tpl.html',
			controller : function($scope) {
				var action = ActionService.get($scope.id);
				$scope.description = action.description;
				$scope.disabled = true;
				$scope.showIcon = angular.isString($scope.icon);
				
				ActionService.verify($scope.id, $scope.resources)
					.then(function(isAllowed) {
						$scope.disabled = isAllowed;
					});
				
				$scope.go = function() {
					$state.go('actions.' + action.context + '.' + action.id);
				};
			}
		}
	}]);
})();

