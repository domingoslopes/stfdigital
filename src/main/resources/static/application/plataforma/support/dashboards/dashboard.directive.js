/**
 * Diretiva para renderizar um dashboard específico.
 * 
 * A especificação dos dashlets que compõem o dashboard é obtida
 * por meio do DashboardService.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
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
	angular.plataforma.directive('dashboard', ['$compile', 'Dashlets', 'DashboardService', function($compile, Dashlets, DashboardService) {
		return {
			restrict : 'ECA',
			scope: {},
			priority: -101,
			templateUrl: 'application/plataforma/support/dashboards/dashboard-layout.tpl.html',
			controller: ['$scope', function($scope) {
				$scope.linhas = [];
				DashboardService.getDashlets().then(function(dashlets) {
					if (dashlets && dashlets.length > 0) {
						var linhaAtual, columnClasses;
						for (var i = 0; i < dashlets.length; i++) {
							if (i % 2 == 0) {
								linhaAtual = {
									colunas: []
								};
								$scope.linhas.push(linhaAtual);
								linhaAtual.colunas.push({dashlet: dashlets[i], columnClasses: ['col-md-6', 'col-lg-6', 'hidden-xlg m-b-10']});
							} else {
								linhaAtual.colunas.push({dashlet: dashlets[i], columnClasses: ['col-md-6', 'col-lg-6', 'hidden-xlg m-b-10']});
							}
						}
					}
				});
			}]
		};
	}]);
	
	angular.plataforma.directive('dashlet', ['$compile', '$timeout', 'Dashlets', function($compile, $timeout, Dashlets) {
		return {
			restrict : 'ECA',
			scope: {
				dashlet: '='
			},
			priority: -200,
			link: function(scope, element, attrs) {
				$timeout(function() {
					var dashletName = scope.dashlet;
					var controller = Dashlets.getDashletController(dashletName);
					var template = Dashlets.getDashletView(dashletName);
					var directiveHtml = '<div data-ng-controller="' + controller + '" data-ng-include="' +
					"'" + template + "'" + '"></div>'
					element.html(directiveHtml);
					
					var link = $compile(element.contents());
					
					link(scope);
				});
			}
		};
	}]);
})();

