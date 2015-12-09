/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('security-interceptor', function ($q, $window, $cookies, properties) {
		
	    this.request = function(config) { 
	        var csrf_token = $cookies.get('XSRF-TOKEN');

	        if (csrf_token) {
	            config.headers['X-XSRF-TOKEN'] = csrf_token;
	        }
	        return config;
	    };

	    this.responseError = function(response) {
	    	// Não filtra se estiver na lista de URLs para não interceptar
			if (properties.urlsNaoInterceptar.indexOf(rejection.config.url) >= -1) {
				return;
			}
			
	        if (response.status == 401 || response.status == 403) {
	        	$window.location.href = '/login';
	        }
	        return $q.reject(response);
	    };
	});
	
})();