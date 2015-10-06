/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('DashboardService', ['$http', '$q', '$window', 'properties', function($http, $q, $window, properties) {

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
			var deferred = $q.defer();
			$http.get(properties.apiUrl + '/dashboards/padrao').success(function(dashboardDto) {
				deferred.resolve(dashboardDto.dashlets);
			}).error(function() {
				deferred.reject();
			});
//			return deferred.promise;
			var papelAtivo = getPapelAtivo();
			return $q.when(mockDashletsFromPapel(papelAtivo));
		};
		
	}]);
})();
