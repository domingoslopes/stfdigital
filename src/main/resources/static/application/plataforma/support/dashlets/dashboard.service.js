/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('DashboardService', ['$http', '$q', '$window', function($http, $q, $window) {

		var getPapelAtivo = function() {
			return JSON.parse($window.sessionStorage.getItem('papel'));
		};
		
		var mockDashletsFromPapel = function(papel) {
			switch (papel.nome) {
			case 'peticionador':
				return ['minhas-peticoes'];
			case 'preautuador':
				return ['peticoes-para-preautuar'];
			case 'autuador':
				return ['grafico-peticoes'];
			case 'distribuidor':
				return ['grafico-distribuicao'];
			default:
				break;
			};
		};
		
		/**
		 * Recupera os dashlets do papel atual.
		 * 
		 * TODO Colocar a chamada real para o back-end.
		 */
		this.getDashlets = function() {
			var papelAtivo = getPapelAtivo();
			return $q.when(mockDashletsFromPapel(papelAtivo));
		};
		
	}]);
})();
