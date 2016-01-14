/**
 * @author Tomas.Godoi
 * @since 1.0.0
 */
(function() {
	'use strict';
	
	/**
	 * @ngdoc directive
	 * @name dashboard
	 * @memberOf plataforma
	 * 
	 * @description Diretiva para renderizar um dashboard específico.
	 * A especificação dos dashlets que compõem o dashboard é obtida por meio do DashboardService.
	 * 
	 * @example
	 * <div data-dashboard=""></div>
	 */
	angular.plataforma.directive('dashboard', ['DashboardLayoutManager', function(DashboardLayoutManager) {
		return {
			restrict : 'EA',
			scope: {
				dashboard : '='
			},
			templateUrl: 'application/plataforma/support/dashboards/dashboard-layout.tpl.html',
			controller: ['$scope', function($scope) {								
				$scope.$watch('dashboard', function() {
					var dashlets = angular.isDefined($scope.dashboard) ? $scope.dashboard.dashlets : [];
					$scope.layout = DashboardLayoutManager.defaultLayout(dashlets);
				});
			}]
		};
	}]);
	
	/**
	 * @ngdoc directive
	 * @name dashboard
	 * @memberOf plataforma
	 * 
	 * @description Diretiva para renderizar um dashboard específico.
	 * A especificação dos dashlets que compõem o dashboard é obtida por meio do DashboardService.
	 * 
	 * @example
	 * <div data-dashboard=""></div>
	 */
	angular.plataforma.directive('dashboardMenu', ['$state', 'DashboardService', function($state, DashboardService) {
		return {
			restrict : 'EA',
			scope: {},
			templateUrl: 'application/plataforma/support/dashboards/dashboard-menu.tpl.html',
			controller: ['$scope', function($scope) {
				$scope.dashboards = [];
				
				DashboardService.getDashboards().then(function(dashboards) {
					$scope.dashboards = dashboards;
				});
				
				$scope.go = function(dashboard) {
					var params = {dashboard : dashboard};
					$state.go('dashboard', params);
				}
			}]
		};
	}]);
	
	/**
	 * @ngdoc directive
	 * @name dashlet
	 * @memberOf plataforma
	 * 
	 * @description Diretiva para renderizar um dashlet.
	 * 
	 * @example
	 * <div data-dashlet="nomeDoDashlet"></div>
	 */
	angular.plataforma.directive('dashlet', ['$compile', '$timeout', 'Dashlets', function($compile, $timeout, Dashlets) {
		return {
			restrict : 'EA',
			scope: {
				dashlet: '='
			},
			link: function(scope, element, attrs) {
				/*
				 * $timeout utilizado para permitir a aplicação do layout antes de
				 * renderizar o dashlet.
				 */
				$timeout(function() { 
					var dashletName = scope.dashlet;
					var controller = Dashlets.getDashletController(dashletName);
					var template = Dashlets.getDashletView(dashletName);
					var directiveHtml = '<div data-ng-controller="' + controller + '" data-ng-include="' +
					"'" + template + "'" + '"></div>';
					element.html(directiveHtml);
					
					var link = $compile(element.contents());
					
					link(scope);
				});
			}
		};
	}]);
})();

