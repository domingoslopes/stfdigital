/**
 * Diretiva para exibição de alerta de mensages.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.directive('messages', function($timeout) {
		var Alert = function(alerts, message, type) {
			var self = this;
			this.type = type;
			this.message = message;
			this.close = function(){
				var index = alerts.indexOf(this);
				alerts.splice(index, 1);
			}
			$timeout(function() {
				self.close();
			}, 3000);
		};
		return {
			restrict: 'EA',
			replace: true,
			templateUrl : 'application/plataforma/support/messaging/messages.tpl.html',
			scope: {
				api: "=?"
			},
			link: function(scope, element, attrs){
				scope.alerts = [];
				scope.api = {
					success: function(message){
						scope.alerts.push(new Alert(scope.alerts, message, 'success'));
					},
					warning: function(message){
						scope.alerts.push(new Alert(scope.alerts, message, 'warning'));
					},
					error: function(message) {
						scope.alerts.push(new Alert(scope.alerts, message, 'danger'));
					}
				}
			}
		}
	});
})();
			