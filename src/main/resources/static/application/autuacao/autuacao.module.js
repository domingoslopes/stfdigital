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
		
		$stateProvider.state('visualizar', {
			abstract: true,
			parent: 'root',
			url: '/visualizacao'
		}).state('visualizar.processo', {
			parent: 'root',
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
		}).state('actions.autuacao', { // estado abstrato para agrupar as ações do contexto
			abstract : true,
			views: {
				'modal@root' : {}
			}
		}).state('registrar-peticao', {
			parent: 'actions.autuacao',
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
			parent: 'actions.autuacao',
			url: '/peticao/fisica',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/registro/registro.tpl.html',
					controller: 'RegistroPeticaoFisicaController'
				}
			}
		}).state('preautuar', {
			parent: 'actions.autuacao',
			url: '/peticao/preautuacao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/preautuacao/preautuacao.tpl.html',
					controller: 'PreautuacaoController'
				}
			}
		}).state('autuar', {
			parent: 'actions.autuacao',
			url: '/peticao/autuacao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/autuacao/autuacao.tpl.html',
					controller: 'AutuacaoController'
				}
			}
		}).state('devolver-peticao', {
			parent: 'actions.autuacao',
			url: '/peticao/devolucao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/devolucao/devolucao.tpl.html'
				}
			}
		}).state('assinar-devolucao-peticao', {
			parent: 'actions.autuacao',
			url: '/peticao/assinatura-devolucao',
			params : { resources : [] },
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/devolucao/assinatura/assinatura-devolucao.tpl.html',
					controller: 'AssinaturaDevolucaoController'
				}
			}
		}).state('distribuir-processo', {
			parent: 'actions.autuacao',
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
		});
		
		DashletsProvider.dashlet('minhas-peticoes', {
			view: 'application/autuacao/peticionamento/dashlets/peticoes.tpl.html',
			controller: 'MinhasPeticoesDashletController'
		}).dashlet('grafico-gestao', {
			view: 'application/autuacao/gestao/dashlets/gestao-autuacao.tpl.html',
			controller: 'GestaoAutuacaoDashletController'
		}).dashlet('peticoes-para-preautuar', {
			view: 'application/autuacao/preautuacao/dashlets/peticoes-preautuar.tpl.html',
			controller: 'MinhasPeticoesParaAutuarDashletController'
		}).dashlet('grafico-peticoes', {
			view: 'application/autuacao/autuacao/dashlets/grafico-peticoes.tpl.html',
			controller: 'GraficoPeticoesDashletController'
		}).dashlet('grafico-distribuicao', {
			view: 'application/autuacao/distribuicao/dashlets/grafico-distribuicao.tpl.html',
			controller: 'GraficoDistribuicaoDashletController'
		}).dashlet('assinador-pdf', {
			view: 'application/autuacao/certification/dashlets/assinador-pdf.tpl.html',
			controller: 'AssinadorPdfDashletController'
		});
	});

})();

