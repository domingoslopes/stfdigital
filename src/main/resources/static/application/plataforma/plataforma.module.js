/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.07.2015
 */ 
(function() {
	'use strict';

	angular.module('plataforma', ['ui.router']).config(function($stateProvider) {
		$stateProvider.state('root.home', {
			url : '/',
			views : {
				'@' : {
					templateUrl : 'application/plataforma/dashboard/dashboard.tpl.html',
					controller : 'DashboardController',
					resolve : {
						data : function(TarefaService) {
							return TarefaService.listar();
						}
					}
				}
			}
		});
	});

})();
