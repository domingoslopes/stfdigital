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
					templateUrl: 'application/autuacao/peticao/fisica/registro.tpl.html',
					controller: 'RegistroPeticaoFisicaController'
				}
			}
		});
	});

})();

