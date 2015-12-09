/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
(function() {
	'use strict';

	angular.plataforma.factory('error-handler', function httpInterceptor($q, $log, $injector, properties) {
		return {
			request : function(config) {
				$log.debug('Request: ', config);
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
				// Não filtra se estiver na lista de URLs para não interceptar
				if (properties.urlsNaoInterceptar.indexOf(rejection.config.url) > -1) {
					return;
				}
					
				$log.debug('Response Error: ', rejection);
				if (rejection.status === 500) {
					$injector.get('$state').go('erro');
				}
				if (rejection.status === 0) {
					return;
				}
				return $q.reject(rejection);
				
			}
		};
	});
	
})();
