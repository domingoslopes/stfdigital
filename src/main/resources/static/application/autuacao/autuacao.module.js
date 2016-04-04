/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao = angular.module('autuacao', []);
	
	angular.autuacao.config(function config($stateProvider, DashletsProvider) {
		
		$stateProvider.state('visualizar.processo', {
			url: '/processo/:processoId',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/visualizacao/processo.tpl.html',
					controller: 'VisualizacaoProcessoController'
				}
			}
		}).state('visualizar.peticao', {
			url: '/peticao/:peticaoId',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/visualizacao/peticao.tpl.html',
					controller: 'VisualizacaoPeticaoController'
				}
			}
		}).state('pesquisar.peticoes', {
			url: '/peticoes',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pesquisa/peticoes.tpl.html',
					controller: 'PesquisaPeticoesController',
					resolve : {
						classes : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('pesquisar.processos', {
			url: '/processos',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pesquisa/processos.tpl.html',
					controller: 'PesquisaProcessosController',
					resolve : {
						classes : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('pesquisar.peticoes.avancada', {
			url: '/avancada',
			params : { pesquisa : undefined },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pesquisa/avancada/peticoes.tpl.html',
					controller: 'PesquisaPeticoesAvancadaController'
				}
			}
		}).state('pesquisar.processos.avancada', {
			url: '/avancada',
			params : { pesquisa : undefined },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pesquisa/avancada/processos.tpl.html',
					controller: 'PesquisaProcessosAvancadaController'
				}
			}
		}).state('action.autuacao', { // estado abstrato para agrupar as ações do contexto
			abstract : true,
			views: {
				'modal@root' : {}
			}
		}).state('registrar-peticao', {
			parent: 'action.autuacao',
			url: '/peticao',
			abstract: true,
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/peticionamento/peticionamento.tpl.html',
					controller: 'PeticionamentoController',
					resolve : {
						data : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('registrar-peticao-eletronica', {
			parent: 'registrar-peticao',
			url: '/advogado',
			params : { resources : [] },
			views: {
				'@registrar-peticao': {
					templateUrl: 'application/autuacao/peticionamento/advogado/peticionamento.tpl.html',
					controller: 'PeticionamentoAdvogadoController'
				}
			}
		}).state('enviar-processo', {
			parent: 'action.autuacao',
			url: '/envio/processo',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/envio/envio-processo.tpl.html',
					controller: 'EnvioProcessoController'
				}
			}
		}).state('registrar-peticao-eletronica-orgao', {
			parent: 'registrar-peticao',
			url: '/orgao',
			params : { resources : [] },
			views: {
				'@registrar-peticao': {
					templateUrl: 'application/autuacao/peticionamento/orgao/peticionamento.tpl.html',
					controller: 'PeticionamentoOrgaoController'
				}
			}
		}).state('registrar-peticao-fisica', {
			parent: 'action.autuacao',
			url: '/peticao/fisica',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/registro/registro.tpl.html'
				}
			}
		}).state('preautuar', {
			parent: 'action.autuacao',
			url: '/peticao/preautuacao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/preautuacao/preautuacao.tpl.html'
				}
			}
		}).state('preautuar-recursal', {
			parent: 'action.autuacao',
			url: '/peticao/preautuacao/recursal',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/preautuacao/preautuacao-recursal.tpl.html'
				}
			}
		}).state('autuar-recursal-criminal-eleitoral',{
			parent: 'action.autuacao',
			url: '/processo/autuacao-criminal',
			params : { resources : [], task : null },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/autuacao/autuacao-criminal.tpl.html'
				}
			}
		}).state('autuar-recursal',{
			parent: 'action.autuacao',
			url: '/processo/autuacao-recursal',
			params : { resources : [], task : null },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/autuacao/autuacao-recursal.tpl.html'
				}
			}	
		}).state('analisar-pressupostos-formais',{
			parent: 'action.autuacao',
			url: '/processo/analise',
			params : { resources : [], task : null },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/pressuposto/analise-pressupostos.tpl.html'
				}
			}	
		}).state('revisar-processo-inapto',{
			parent: 'action.autuacao',
			url: '/processo/revisao',
			params : { resources : [], task : null },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/revisao/revisao-processo-inapto.tpl.html'
				}
			}
		}).state('analisar-repercussao-geral',{
			parent: 'action.autuacao',
			url: '/processo/repercussao',
			params : { resources : [], task : null },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/repercussao/analise-repercussao-geral.tpl.html'
				}
			}
		}).state('revisar-repercussao-geral', {
			parent: 'action.autuacao',
			url: '/processo/repercussao/revisar',
			params : { resources : [], task : null },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/recursal/repercussao/revisao-repercussao-geral.tpl.html'
				}
			}
		}).state('autuar', {
			parent: 'action.autuacao',
			url: '/peticao/autuacao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/autuacao/autuacao.tpl.html'
				}
			}
		}).state('finalizar-texto-devolucao', {
			parent: 'action.autuacao',
			url: '/peticao/devolucao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/devolucao/devolucao.tpl.html'
				}
			}
		}).state('devolver-peticao', {
			parent: 'action.autuacao',
			url: '/peticao/assinatura-devolucao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/devolucao/assinatura/assinatura-devolucao.tpl.html',
					controller: 'AssinaturaDevolucaoController'
				}
			}
		}).state('distribuir-processo', {
			parent: 'action.autuacao',
			url: '/peticao/distribuicao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/distribuicao/distribuicao.tpl.html',
					controller: 'DistribuicaoController',
					resolve : {
						data : function(MinistroService) {
							return MinistroService.listar();
						}
					}
				}
			}
		}).state('organizar-pecas', {
			parent: 'action.autuacao',
			url: '/processo/pecas',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pecas/organiza-pecas.tpl.html',
					controller: 'OrganizaPecasController',
					controllerAs: 'organiza'
				}
			}
		}).state('inserir-pecas', {
			parent: 'action.autuacao',
			url: '/autuacao/pecas',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pecas/insere-pecas.tpl.html',
					controller: 'InserePecasController'
				}
			}
		}).state('juntar-peca', {
			parent: 'action',
			params : { resources : [] },
			views: {
				'@action': {
					templateUrl: 'application/autuacao/pecas/juntar-peca.tpl.html',
					controller: 'JuntaPecasController'
				}
			}
		}).state('excluir-pecas', {
			parent: 'action',
			params : { resources : [] },
			views: {
				'@action': {
					templateUrl: 'application/autuacao/pecas/excluir-pecas.tpl.html',
					controller: 'ExcluiPecasController'
				}
			}
		}).state('unir-pecas', {
			parent: 'action',
			params : { resources : [] },
			views: {
				'@action': {
					templateUrl: 'application/autuacao/pecas/unir-pecas.tpl.html',
					controller: 'UnePecasController'
				}
			}
		}).state('dividir-peca', {
			parent: 'action',
			params : { resources : [], modalClass:"modal-lg"},
			views: {
				'@action': {
					templateUrl: 'application/autuacao/pecas/dividir-pecas.tpl.html',
					controller: 'DividePecasController'
				}
			}
		}).state('editar-peca', {
			parent: 'action',
			params : { resources : [], modalClass:"modal-lg" },
			views: {
				'@action': {
					templateUrl: 'application/autuacao/pecas/edita-peca.tpl.html',
					controller: 'EditaPecaController'
				}
			}
		});
		
		DashletsProvider.dashlet('minhas-peticoes', {
			view: 'application/autuacao/dashlets/peticoes.tpl.html',
			controller: 'MinhasPeticoesDashletController'
		}).dashlet('grafico-gestao', {
			view: 'application/autuacao/dashlets/gestao-autuacao.tpl.html',
			controller: 'GestaoAutuacaoDashletController'
		}).dashlet('peticoes-para-preautuar', {
			view: 'application/autuacao/dashlets/peticoes-preautuar.tpl.html',
			controller: 'MinhasPeticoesParaAutuarDashletController'
		}).dashlet('grafico-peticoes', {
			view: 'application/autuacao/dashlets/grafico-peticoes.tpl.html',
			controller: 'GraficoPeticoesDashletController'
		}).dashlet('grafico-distribuicao', {
			view: 'application/autuacao/dashlets/grafico-distribuicao.tpl.html',
			controller: 'GraficoDistribuicaoDashletController'
		});
	});

})();

