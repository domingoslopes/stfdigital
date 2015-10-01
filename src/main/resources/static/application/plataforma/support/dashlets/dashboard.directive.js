/**
 * TODO Tomas.Godoi Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	angular.plataforma.directive('dashboard', ['dashlets', function(dashlet) {
		return {
			restrict : 'A',
			scope : {
			},
			template : '<div>Teste</div>',
			controller : function($scope) {
				console.log('teste');
			}
		};
	}]);
	
})();

