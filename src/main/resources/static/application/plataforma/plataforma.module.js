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
	
	angular.plataforma = angular.module('plataforma', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous', 'nvd3', 'angularMoment', 'ngMask', 'checklist-model', 'frapontillo.bootstrap-switch']);
	angular.plataforma.config(function($stateProvider, DashletsProvider) {
		$stateProvider.state('dashboard', {
			parent: 'root',
			url : '/dashboard',
			deepStateRedirect: true,
		    sticky: true,
			params : { dashboard : {} },
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
					controller: 'RegistrarAssociadoController',
					controllerAs: 'vm'
				}
			}
		})		
		.state('configurar-permissao', {
			parent: 'root',
			url: '/permissoes',
			views: {
				'content@root': {
					templateUrl: 'application/plataforma/acessos/permissoes/permissoes.tpl.html',
					controller: 'PermissoesController',
					controllerAs: 'vm'
				}
			}
		})
		.state('informacoes-usuario', {
			parent: 'root',
			url: '/usuario',
			views: {
				'content@root': {
					templateUrl: 'application/plataforma/identidades/usuario/informacoes.tpl.html',
					controller: 'InformacoesController',
					controllerAs: 'vm'
				}
			}
		})
		.state('assumir-tarefa', {
			parent: 'action',
			params : { resources : [] },
			views: {
				'@action': {
					templateUrl: 'application/plataforma/workflow/tarefa/assumir.tpl.html',
					controller: 'AssumirController'
				}
			}
		})
		.state('delegar-tarefa', {
			parent: 'action',
			params : { resources : [] },
			views: {
				'@action': {
					templateUrl: 'application/plataforma/workflow/tarefa/delegar.tpl.html',
					controller: 'DelegarController'
				}
			}
		});
		
		DashletsProvider.dashlet('minhas-tarefas', {
			view: 'application/plataforma/dashlets/minhas-tarefas.tpl.html',
			controller: 'MinhasTarefasDashletController'
		}).dashlet('tarefas-papeis', {
			view: 'application/plataforma/dashlets/tarefas-papeis.tpl.html',
			controller: 'TarefasPapeisDashletController'
		});
	});
	
	// Configurando o locale do angularMoment para pt_br
	angular.plataforma.run(['amMoment', function(amMoment) {
		amMoment.changeLocale('pt_br');
	}]);

})();

