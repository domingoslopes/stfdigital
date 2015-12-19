/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 * @since 10.12.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('Crypto', function() {
		this.use = function(backend) {
			return hwcrypto.use(backend);
		};
		
		this.getCertificate = function(options) {
			return hwcrypto.getCertificate(options);
		};
		
		this.sign = function(certificate, data, options) {
			return hwcrypto.sign(certificate, {type: data.type, hex: data.hex}, options);
		};
	});
	
})();
