/**
 * Provider Dashlets
 * 
 * Este provider permite o registros de dashlets pelos
 * módulos da aplicação.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.provider('dashlets', [function() {
		var definedDashlets = [];
		var defaultDashletTemplate = '';
		
		this.defaultTemplate = function(path) {
			defaultDashletTemplate = path;
			return this;
		};
		
		this.dashlet = function(name, definition) {
			definedDashlets.push({'name': name, 'definition': definition});
			return this;
		};
		
		var getDashletDefinition = function(name) {
			for (var i in definedDashlets) {
				if (definedDashlets[i].name == name) {
					return definedDashlets[i].definition;
				}
			}
			throw "Dashlet " + name + " não foi encontrada.";
		};
		
		var Dashlets = function() {
			this.getDefaultDashletTemplate = function() {
				return defaultDashletTemplate;
			};
			this.getDashletView = function(name) {
				return getDashletDefinition(name).view;
			};
			this.getDashletController = function(name) {
				return getDashletDefinition(name).controller;
			};
		};
		
		this.$get = [function() {
			return new Dashlets();
		}];
	}]);
})();
