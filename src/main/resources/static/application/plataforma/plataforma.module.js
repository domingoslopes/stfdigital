/**
 * @namespace plataforma
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.07.2015
 */ 
(function() {
	'use strict';
	
	angular.plataforma = angular.module('plataforma', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous', 'nvd3', 'angularMoment', 'ngMask', 'checklist-model']);
	angular.plataforma.config(function($stateProvider, DashletsProvider) {
		$stateProvider.state('dashboard', {
			parent: 'root',
			url : '/dashboard',
			views: {
				'content@root': {
					templateUrl : 'application/plataforma/dashboard/dashboard.tpl.html',
					controller : 'DashboardController'
				}
			}
		}).state('erro', {
			parent: 'root',
			url : '/erro',
			templateUrl : 'application/plataforma/support/error-handling/error.tpl.html'
		})
		
		.state('registrar-associado', {
			parent: 'root',
			url: '/associado',
			views: {
				'content@root': {
					templateUrl: 'application/plataforma/identidades/associado/registrar.tpl.html',
					controller: 'RegistrarAssociadoController'
				}
			}
		});
		
		DashletsProvider.dashlet('minhas-tarefas', {
			view: 'application/plataforma/workflow/dashlets/minhas-tarefas.tpl.html',
			controller: 'MinhasTarefasDashletController'
		});
	});
	
	// Configurando o locale do angularMoment para pt_br
	angular.plataforma.run(['amMoment', function(amMoment) {
		amMoment.changeLocale('pt_br');
	}]);

})();

