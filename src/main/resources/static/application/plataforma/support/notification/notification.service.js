/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';
	
	angular.plataforma.service('NotificationService', ['$http', '$q', function($http, $q) {
		var Notificacao = function(texto, hora, lido) {
			this.texto = texto;
			this.hora = hora;
			this.lido = lido;
		};
		
		this.list = function() {
			return $q.when([new Notificacao('Esse é um exemplo de notificação.', new Date(), true), new Notificacao('Você foi intimado no processo.', new Date(), false),
			                new Notificacao('Você tem novas petições para autuar.', new Date(), false),
			                new Notificacao('Você tem novas petições para autuar 2.', new Date(), false),
			                new Notificacao('Você tem novas petições para autuar 3.', new Date(), false),
			                new Notificacao('Você tem novas petições para autuar 4.', new Date(), false),
			                new Notificacao('Você tem novas petições para autuar 5.', new Date(), false)]);
		};
		
		this.marcarComoLido = function(notification) {
			return $q.when();
		};
	}]);
})();