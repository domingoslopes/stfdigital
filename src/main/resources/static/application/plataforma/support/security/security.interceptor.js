/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('security-interceptor', function ($window, $cookies) {
		
	    this.request = function(config) { 
	        var csrf_token = $cookies.get('XSRF-TOKEN');

	        if (csrf_token) {
	            config.headers['X-XSRF-TOKEN'] = csrf_token;
	        }
	        return config;
	    };

	    this.responseError = function(response) {
	        if (response.status == 401 || response.status == 403) {
	        	$window.location.href = '/login';
	        }
	        return response;
	    };
	});
	
})();