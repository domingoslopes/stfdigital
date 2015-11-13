/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.autuacao.service('AssinaturaService', ['$http', '$q', function($http, $q) {
		var crypto = hwcrypto;
		
		this.requestUserCertificate = function() {
			return $q(function(resolve, reject) {
				crypto.getCertificate({lang: 'en'}).then(function(response) {
					console.log(response);
				}, function(err) {
					console.log(err);
				});
			});
		};
		
	}]);
	
})();
