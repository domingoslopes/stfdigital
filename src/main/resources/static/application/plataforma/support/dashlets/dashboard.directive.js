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
(function() {
	'use strict';
	
	angular.plataforma.directive('dashboard', ['$compile', 'Dashlets', 'DashboardService', function($compile, Dashlets, DashboardService) {
		return {
			restrict : 'ECA',
			priority: 400,
			link: function(scope, element, attrs) {
				console.log('link');
				var dashletName = 'minhas-peticoes';
				var controller = Dashlets.getDashletController(dashletName);
				var template = Dashlets.getDashletView(dashletName);
				
				DashboardService.getDashlets().then(function(dashes) {
					console.log(dashes);
				});
				
				element.html('<div class="row"><div class="col-md-6 col-lg-6 hidden-xlg m-b-10"><div data-ng-controller="' + controller + '" data-ng-include="' +
						"'" + template + "'" + '"></div></div></div>');
				
				var link = $compile(element.contents());
				
				link(scope);
			}
		};
	}]);
	
	angular.plataforma.directive('fastDashboard', ['$compile', 'Dashlets', 'DashboardService', function($compile, Dashlets, DashboardService) {
		return {
			restrict : 'ECA',
			priority: 400,
			compile: function(tElement) {
				return function(scope, $element, attrs) {
					var dashletName = 'minhas-peticoes';
					var controller = Dashlets.getDashletController(dashletName);
					var template = Dashlets.getDashletView(dashletName);
					
					var html = '<div class="row"><div class="col-md-6 col-lg-6 hidden-xlg m-b-10">' + 'Teste' + '</div></div>';
					
					$element.html(html);
					var link = $compile($element.contents());
					$element.data('$ngControllerController', controller);
					$element.children().data('$ngControllerController', controller);
					
					link(scope);
				};
			}
		};
	}]);
})();

