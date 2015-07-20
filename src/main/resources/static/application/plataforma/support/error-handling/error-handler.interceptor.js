/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
(function() {
	'use strict';

	angular.module('plataforma').factory('error-handler', function httpInterceptor($q, $log, $window, $injector) {
		return {
			request : function(config) {
				$log.debug('Request: ', config);
				config.headers['papel'] = JSON.parse($window.sessionStorage["papel"]).nome;
				return config;
			},
			requestError : function(rejection) {
				$log.debug('Request Error: ', rejection);
				return $q.reject(rejection);
			},
			response : function(response) {
				$log.debug('Response: ', response);
				return response;
			},
			responseError : function(rejection) {
				$log.debug('Response Error: ', rejection);
				if (rejection.status === 500) {
					$injector.get('$state').go('erro');
				}
				return $q.reject(rejection);
				
			}
		};
	});
	
})();
