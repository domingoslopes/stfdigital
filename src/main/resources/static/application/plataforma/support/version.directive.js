/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
(function() {
	'use strict';

	function versionDirective(version) {
		return {
			restrict : 'A',
			/* jshint unused:false */
			link : function(scope, elm, attrs) {
				elm.text(version);
			}
		};
	}

	angular.module('version', []).directive('appVersion', versionDirective);
})();
