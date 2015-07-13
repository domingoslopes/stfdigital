/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 09.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('autuacao', []).config(function config($stateProvider) {
		$stateProvider.state('root.peticao-fisica', {
			url: '/peticao-fisica',
			views: {
				'@': {
					templateUrl: 'application/autuacao/registro/registro.tpl.html',
					controller: 'RegistroPeticaoFisicaController'
				}
			}
		});
		$stateProvider.state('root.preautuacao', {
			url: '/peticao/preautuacao/:idTarefa',
			views: {
				'@': {
					templateUrl: 'application/autuacao/preautuacao/preautuacao.tpl.html',
					controller: 'PreautuacaoController'
				}
			}
		});
		$stateProvider.state('root.autuacao', {
			url: '/peticao/autuacao/:idTarefa',
			views: {
				'@': {
					templateUrl: 'application/autuacao/autuacao/autuacao.tpl.html',
					controller: 'AutuacaoController'
				}
			}
		});
		$stateProvider.state('root.distribuicao', {
			url: '/peticao/distribuicao/:idTarefa',
			views: {
				'@': {
					templateUrl: 'application/autuacao/distribuicao/distribuicao.tpl.html',
					controller: 'DistribuicaoController'
				}
			}
		});
		$stateProvider.state('root.devolucao', {
			url: '/peticao/devolucao/:idTarefa',
			views: {
				'@': {
					templateUrl: 'application/autuacao/devolucao/devolucao.tpl.html',
					controller: 'DevolucaoController'
				}
			}
		});
	});

})();

