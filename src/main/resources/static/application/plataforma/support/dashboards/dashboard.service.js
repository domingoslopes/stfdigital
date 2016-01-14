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
		 * @function getDashboards
		 * @memberOf DashboardService
		 * 
		 * @description Recupera os dashboards do usuário.
		 * 
		 */
		this.getDashboards = function() {
			var deferred = $q.defer();
			$http.get(properties.apiUrl + '/dashboards').success(function(dashboards) {
				deferred.resolve(dashboards);
			}).error(function() {
				deferred.reject();
			});
			return deferred.promise;
		};
		
	}]);
})();
