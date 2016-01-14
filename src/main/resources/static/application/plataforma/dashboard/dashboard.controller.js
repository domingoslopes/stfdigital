// Essa dashlet controlará os aspectos mais abrangentes do dashboard, não se responsabilizando
// mais pela exibição de Minhas Tarefas e Minhas Petições 
/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('DashboardController', function ($scope, $rootScope, $stateParams, DashboardService) {
		
		$scope.dashboard = {};
		
		if (angular.isDefined($stateParams.dashboard.dashlets)) {
			$scope.dashboard = $stateParams.dashboard;
		} else {
			//TODO enquanto não existe um dashboard padrão, o primeiro será usado
			DashboardService.getDashboards().then(function(dashboards) {
				var lastPos = (dashboards.length > 0) ? dashboards.length - 1 : 0;
				$scope.dashboard = dashboards[lastPos];
			});
		}
	});
	
})();

