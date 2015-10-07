/**
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
		};
		
	}]);
})();
