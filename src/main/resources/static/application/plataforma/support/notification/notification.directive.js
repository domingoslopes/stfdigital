/**
 * @author Tomas.Godoi
 * @since 1.0.0
 */
(function() {
	'use strict';
	
	angular.plataforma.directive('notificationCenter', ['NotificationService', function(NotificationService) {
		return {
			restrict: 'EA',
			scope: {},
			replace: true,
			templateUrl: 'application/plataforma/support/notification/notification-center.tpl.html',
			controller: function($scope) {
				$scope.notifications = [];
				$scope.defaultNotificationTemplate = 'application/plataforma/support/notification/templates/default.tpl.html';
				
				NotificationService.list().then(function(notifications) {
					$scope.notifications = notifications;
				});
				
				$scope.marcarComoLido = function(notification) {
					NotificationService.marcarComoLido(notification).then(function() {
						notification.lido = true;
					});
				};
				
				$scope.lerTodas = false;
				$scope.mostrarTodas = function() {
					$scope.lerTodas = true;
				};
				
				$scope.getMaxNotifications = function() {
					return $scope.lerTodas ? $scope.notifications.length : 5;
				};
			}
		};
	}]);
	
})();