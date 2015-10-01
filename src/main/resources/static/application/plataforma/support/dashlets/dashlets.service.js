/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	angular.plataforma.service('DashletsService', ['$http', '$q', function($http, $q) {
		
		this.getDashlets = function() {
			return $q.when(['minhas-peticoes']);
		};
		
	}]);
})();
