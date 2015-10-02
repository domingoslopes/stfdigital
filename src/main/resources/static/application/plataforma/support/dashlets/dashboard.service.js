/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('DashboardService', ['$http', '$q', function($http, $q) {
		
		/**
		 * Recupera os dashlets do papel atual.
		 * 
		 * TODO Colocar a chamada real para o back-end.
		 */
		this.getDashlets = function() {
			return $q.when(['minhas-peticoes']);
		};
		
	}]);
})();
