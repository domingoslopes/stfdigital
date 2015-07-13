/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
(function() {
	'use strict';

	angular.module('plataforma').factory('error-handler', function httpInterceptor($q, $log) {
		return {
			request : function(config) {
				return config;
			},
			requestError : function(rejection) {
				$log.debug(rejection);
				return $q.reject(rejection);
			},
			response : function(response) {
				$log.debug('response: ', response);
				return response;
			},
			responseError : function(rejection) {
				$log.debug(rejection);
				return $q.reject(rejection);
			}
		};
	});
	
})();
