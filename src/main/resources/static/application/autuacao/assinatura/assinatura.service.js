/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */
(function() {
	'use strict';

	angular.autuacao.service('AssinaturaService', ['properties', '$http', '$q', function(properties, $http, $q) {
		var crypto = hwcrypto;
		
		if (crypto.use('auto')) {
			console.log('backend detectado com sucesso');
		}
		
		this.requestUserCertificate = function() {
			return $q(function(resolve, reject) {
				crypto.getCertificate({lang: 'en'}).then(function(response) {
					console.log(response);
					resolve(response);
				}, function(err) {
					console.log(err);
					reject(err);
				});
			});
		};
		
		this.preSign = function(command) {
			return $q(function(resolve, reject) {
				$http.post(properties.apiUrl + '/certification/pre-sign', command).success(function(dto) {
					console.log(dto);
					resolve(dto);
				}).error(function(error) {
					console.log(error);
					reject(error);
				});
			});
		};
		
		this.sign = function(cert, hash, hashType) {
			return $q(function(resolve, reject) {
				crypto.sign(cert, {type: 'SHA-256', hex: hash}, {lang: 'en'}).then(function(response) {
					console.log(response);
					resolve(response.hex);
				}, function(err) {
					console.log(err);
					reject(err);
				});
			})
		};
		
	}]);
	
})();
