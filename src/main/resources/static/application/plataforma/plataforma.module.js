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
	
	angular.plataforma = angular.module('plataforma', ['ui.router', 'ct.ui.router.extras.sticky', 'ct.ui.router.extras.previous', 'nvd3', 'angularMoment', 'ngMask', 'checklist-model', 'frapontillo.bootstrap-switch', 'datatables', 'ui.sortable', 'ngCkeditor', 'onlyoffice']);
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
		}).state('criar-modelo', {
			parent: 'root',
			url: '/modelo/criar',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/plataforma/modelo/criacao-modelo.tpl.html',
					controller: 'CriacaoModeloController',
					controllerAs: 'cmc'
				}
			}
		}).state('editar-modelo', {
			parent: 'root',
			url: '/modelo/:idModelo/editar',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/plataforma/modelo/edicao-conteudo-modelo.tpl.html',
					controller: 'EdicaoConteudoModeloController',
					controllerAs: 'ecmc'
				}
			}
		});
		
		DashletsProvider.dashlet('minhas-tarefas', {
			view: 'application/plataforma/dashlets/minhas-tarefas.tpl.html',
			controller: 'MinhasTarefasDashletController'
		}).dashlet('tarefas-papeis', {
			view: 'application/plataforma/dashlets/tarefas-papeis.tpl.html',
			controller: 'TarefasPapeisDashletController'
		}).dashlet('modelos', {
			view: 'application/plataforma/dashlets/modelos.tpl.html',
			controller: 'ModelosDashletController'
		});
	});
	
	// Configurando o locale do angularMoment para pt_br
	angular.plataforma.run(['amMoment', 'DTDefaultOptions', function(amMoment, DTDefaultOptions) {
		amMoment.changeLocale('pt_br');
		
		//DTDefaultOptions.setLanguageSource('/vendor/datatables.net-plugins/i18n/Portuguese-Brasil.lang');
		
		 DTDefaultOptions.setLanguage({
		    "sEmptyTable": "Nenhum registro encontrado",
		    "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
		    "sInfoEmpty": "Mostrando 0 até 0 de 0 registros",
		    "sInfoFiltered": "(Filtrados de _MAX_ registros)",
		    "sInfoPostFix": "",
		    "sInfoThousands": ".",
		    "sLengthMenu": "_MENU_ resultados por página",
		    "sLoadingRecords": "Carregando...",
		    "sProcessing": "Processando...",
		    "sZeroRecords": "Nenhum registro encontrado",
		    "sSearch": "Pesquisar",
		    "oPaginate": {
		        "sNext": "Próximo",
		        "sPrevious": "Anterior",
		        "sFirst": "Primeiro",
		        "sLast": "Último"
		    },
		    "oAria": {
		        "sSortAscending": ": Ordenar colunas de forma ascendente",
		        "sSortDescending": ": Ordenar colunas de forma descendente"
		    }
		});
	}]);

})();

