/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.provider('dashlets', [function() {
		var dashlets = {
			
		};
		
		this.defaultTemplate = function(path) {
			return this;
		};
		
		this.dashlet = function(name, definition) {
			return this;
		};
		
		this.$get = [function() {
			return dashlets;
		}];
	}]);
})();
