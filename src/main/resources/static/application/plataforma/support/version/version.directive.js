/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
(function() {
	'use strict';

	angular.plataforma.directive('appVersion', function(version) {
		return {
			restrict : 'A',
			/* jshint unused:false */
			link : function(scope, elm, attrs) {
				elm.text(version);
			}
		};
	});
	
})();
