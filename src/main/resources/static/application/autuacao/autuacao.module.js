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
		$stateProvider.state('peticionamento', {
			url: '/peticao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/peticionamento/peticionamento.tpl.html',
					controller: 'PeticionamentoController',
					resolve : {
						data : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('peticionamento.advogado', {
			views: {
				'@peticionamento': {
					templateUrl: 'application/autuacao/peticionamento/advogado/peticionamento.tpl.html',
					controller: 'PeticionamentoAdvogadoController'
				}
			}
		}).state('peticionamento.orgao', {
			url: '/orgao',
			views: {
				'@peticionamento': {
					templateUrl: 'application/autuacao/peticionamento/orgao/peticionamento.tpl.html',
					controller: 'PeticionamentoOrgaoController'
				}
			}
		}).state('registro', {
			url: '/peticao/fisica',
			views: {
				'@': {
					templateUrl: 'application/autuacao/registro/registro.tpl.html',
					controller: 'RegistroPeticaoFisicaController'
				}
			}
		}).state('preautuacao', {
			url: '/peticao/:idTarefa/preautuacao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/preautuacao/preautuacao.tpl.html',
					controller: 'PreautuacaoController'
				}
			}
		}).state('autuacao', {
			url: '/peticao/:idTarefa/autuacao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/autuacao/autuacao.tpl.html',
					controller: 'AutuacaoController'
				}
			}
		}).state('distribuicao', {
			url: '/peticao/:idTarefa/distribuicao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/distribuicao/distribuicao.tpl.html',
					controller: 'DistribuicaoController',
					resolve : {
						data : function(MinistroService) {
							return MinistroService.listar();
						}
					}
				}
			}
		}).state('devolucao', {
			url: '/peticao/:idTarefa/devolucao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/devolucao/devolucao.tpl.html',
					controller: 'DevolucaoController'
				}
			}
		}).state('processos', {
			url: '/processos/:processoId',
			views: {
				'@': {
					templateUrl: 'application/autuacao/visualizacao/processos/visualizacao.tpl.html',
					controller: 'VisualizacaoProcessoController'
				}
			}
		}).state('pesquisa.peticao', {
			url: '/peticao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/pesquisa/peticao.tpl.html',
					controller: 'PesquisaPeticaoController',
					resolve : {
						classes : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('pesquisa.processo', {
			url: '/processo',
			views: {
				'@': {
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
		}).dashlet('gestao-autuacao', {
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
		});
	});

})();

