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
		$stateProvider.state('root.peticionamento', {
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
		}).state('root.peticionamento.advogado', {
			url: '/advogado',
			views: {
				'@root.peticionamento': {
					templateUrl: 'application/autuacao/peticionamento/advogado/peticionamento.tpl.html',
					controller: 'PeticionamentoAdvogadoController'
				}
			}
		}).state('root.peticionamento.orgao', {
			url: '/orgao',
			views: {
				'@root.peticionamento': {
					templateUrl: 'application/autuacao/peticionamento/orgao/peticionamento.tpl.html',
					controller: 'PeticionamentoOrgaoController'
				}
			}
		}).state('root.registro', {
			url: '/peticao/fisica',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/registro/registro.tpl.html',
					controller: 'RegistroPeticaoFisicaController'
				}
			}
		}).state('root.preautuacao', {
			url: '/peticao/:idTarefa/preautuacao',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/preautuacao/preautuacao.tpl.html',
					controller: 'PreautuacaoController'
				}
			}
		}).state('root.autuacao', {
			url: '/peticao/:idTarefa/autuacao',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/autuacao/autuacao.tpl.html',
					controller: 'AutuacaoController'
				}
			}
		}).state('root.distribuicao', {
			url: '/peticao/:idTarefa/distribuicao',
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
		}).state('root.devolucao', {
			url: '/peticao/:idTarefa/devolucao',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/devolucao/devolucao.tpl.html',
					controller: 'DevolucaoController'
				}
			}
		}).state('root.processos', {
			url: '/processos/:processoId',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/visualizacao/processos/visualizacao.tpl.html',
					controller: 'VisualizacaoProcessoController'
				}
			}
		}).state('root.pesquisa.peticao', {
			url: '/peticao',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pesquisa/peticao.tpl.html',
					controller: 'PesquisaPeticaoController',
					resolve : {
						classes : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('root.pesquisa.processo', {
			url: '/processo',
			views: {
				'content@root': {
					templateUrl: 'application/autuacao/pesquisa/processo.tpl.html',
					controller: 'PesquisaProcessoController',
					resolve : {
						classes : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('actions.autuacao', { // estado abstrato para agrupar as ações do contexto
			abstract : true
		}).state('actions.autuacao.dummy_action', {
			views: {
				/*
				'modal@' : {}, //faz com que não mostre no modal
				'@' : { // faz com que apareça na view principal
					templateUrl: 'application/autuacao/devolucao/devolucao.tpl.html',
					controller: 'DevolucaoController'
				},
				*/
				'@actions' : {
					templateUrl: 'application/autuacao/registro/dummy_action.tpl.html',
					controller: 'DummyActionController'
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
			view: 'application/autuacao/assinatura/dashlets/assinador-pdf.tpl.html',
			controller: 'AssinadorPdfDashletController'
		});
	});

})();

