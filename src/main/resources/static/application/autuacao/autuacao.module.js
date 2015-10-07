/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao = angular.module('autuacao', []);
	
	angular.autuacao.config(function config($stateProvider) {
		$stateProvider.state('peticionamento', {
			url: '/peticao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/peticionamento/advogado/peticionamento.tpl.html',
					controller: 'PeticionamentoController',
					resolve : {
						data : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('peticionamento-orgao', {
			url: '/peticao/orgao',
			views: {
				'@': {
					templateUrl: 'application/autuacao/peticionamento/orgao/peticionamento.tpl.html',
					controller: 'PeticionamentoController',
					resolve : {
						data : function(ClasseService) {
							return ClasseService.listar();
						}
					}
				}
			}
		}).state('registro', {
			url: '/peticao/fisica',
			/*sticky : true,
			deepStateRedirect : true,*/
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
	});

})();

