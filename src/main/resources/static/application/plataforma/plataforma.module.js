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
	
	angular.plataforma = angular.module('plataforma', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous', 'nvd3']);

	angular.plataforma.config(function($stateProvider, DashletsProvider) {
		$stateProvider.state('dashboard', {
			url : '/dashboard',
			templateUrl : 'application/plataforma/dashboard/dashboard.tpl.html',
			controller : 'DashboardController',
			resolve : {
				data : function(TarefaService) {
					return TarefaService.listar();
				}
			}
		}).state('detalhe', {
			url: '/detalhe/peticao/:idPeticao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/detalhe/detalhepeticao.tpl.html',
					controller: 'DetalhePeticaoController'
				}
			}
		}).state('erro', {
			url : '/erro',
			templateUrl : 'application/plataforma/support/error-handling/error.tpl.html'
		});
		
		DashletsProvider.defaultTemplate('application/plataforma/support/dashlet/dashlet.tpl.html');
	});

})();

