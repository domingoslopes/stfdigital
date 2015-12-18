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

	angular.plataforma.controller('DashboardController', function ($scope, $stateParams) {
		$scope.dashboard = $stateParams.dashboard;
	});
	
})();

