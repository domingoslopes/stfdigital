/**
 * @author Tomas.Godoi
 * @since 1.0.0
 */
(function() {
	'use strict';
	
	angular.plataforma.directive('notificationCenter', ['NotificationService', '$window', function(NotificationService, $window) {
		return {
			restrict: 'EA',
			scope: {},
			replace: true,
			templateUrl: 'application/plataforma/support/notification/notification-center.tpl.html',
			controller: function($scope) {
				$scope.notificacoes = [];
				$scope.defaultNotificationTemplate = 'application/plataforma/support/notification/templates/default.tpl.html';
				
				var receberNotificacao = function(notificacao) {
					$scope.notificacoes.push(notificacao);
				};
				
				NotificationService.registrarNotificacao(receberNotificacao);
				NotificationService.listarNaoLidas();
				
				$scope.marcarComoLida = function(notificacao) {
					var lidas = [];
					lidas.push(notificacao);
					NotificationService.marcarComoLida(lidas);
				};
				
				$scope.marcarTodas = function() {
					NotificationService.marcarComoLida($scope.notificacoes);
				};
				
				$scope.mostrarTodas = function() {
					NotificationService.listarLidas($scope.notificacoes);
				};
				
			},
			link: function(scope, element) {
				element.find('.dropdown-menu').click(function(event) {
					event.stopPropagation();
				});
			}
		};
	}]);
	
})();