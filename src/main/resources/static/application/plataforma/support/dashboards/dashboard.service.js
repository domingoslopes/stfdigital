/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name DashboardService
	 * @memberOf plataforma
	 * 
	 * @description Service para fornecer informações sobre Dashboards.
	 * 
	 */	
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
			case 'recebedor':
				return ['grafico-distribuicao', 'minhas-peticoes', 'grafico-peticoes', 'peticoes-para-preautuar'];
			default:
				break;
			};
		};
		
		/**
		 * @function getDashlets
		 * @memberOf DashboardService
		 * 
		 * @description Recupera os dashlets do papel atual.
		 * 
		 */
		this.getDashlets = function() {
			var deferred = $q.defer();
			$http.get(properties.apiUrl + '/dashboards/padrao').success(function(dashboardDto) {
				deferred.resolve(dashboardDto.dashlets);
			}).error(function() {
				deferred.reject();
			});
			return deferred.promise;
//			var papelAtivo = getPapelAtivo();
//			return $q.when(mockDashletsFromPapel(papelAtivo));
		};
		
	}]);
})();
