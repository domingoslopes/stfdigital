// TODO Tomas.Godoi Incluir mais validações dos métodos de configuração.

/**
 * @author Tomas.Godoi
 * @since 1.0.0
 */
(function() {
	'use strict';

	/**
	 * @ngdoc provider
	 * @name DashletsProvider
	 * @memberOf plataforma
	 * 
	 * @description Este provider permite o registro de dashlets pelos módulos da aplicação
	 * 
	 * @example
	 * angular.module('meu-modulo', ['plataforma']).config(['DashletsProvider', function(DashletsProvider) {
	 *     DashletsProvider.defaultTemplate("dashlet-template.tpl.html")
	 *     .dashlet('dashlet-01', {
	 *         view: 'view-01.tpl.html',
	 *         controller: 'Dashlet01Controller'
	 *     });
	 * }]);
	 */
	angular.plataforma.provider('Dashlets', [function() {
		var definedDashlets = [];
		var defaultDashletTemplate = '';
		
		/**
		 * @function defaultTemplate
		 * @param {string} path O caminho para o template
		 * @memberOf DashletsProvider
		 * 
		 * @description Define o caminho do template padrão de dashlet
		 * 
		 */
		this.defaultTemplate = function(path) {
			defaultDashletTemplate = path;
			return this;
		};
		
		/**
		 * @function dashlet
		 * @description Define um dashlet.
		 * @param id {string} Id do dashlet
		 * @param definition {object} Objeto de definição do dashlet
		 * 
		 * @memberOf DashletsProvider 
		 */
		this.dashlet = function(id, definition) {
			definedDashlets.push({'id': id, 'definition': definition});
			return this;
		};
		
		var getDashletDefinition = function(id) {
			for (var i in definedDashlets) {
				if (definedDashlets[i].id == id) {
					return definedDashlets[i].definition;
				}
			}
			throw new Error("Dashlet " + id + " não foi encontrado.");
		};
		
		var Dashlets = function() {
			this.getDashletView = function(id) {
				return getDashletDefinition(id).view;
			};
			this.getDashletController = function(id) {
				return getDashletDefinition(id).controller;
			};
		};
		
		this.$get = [function() {
			return new Dashlets();
		}];
	}]);
})();
