/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('DummyActionController', function ($stateParams, $scope, messages) {
		var action = this;

		action.resources = $stateParams.resources;
		action.verificar = function(result) {
			messages.success("Sucesso");
			$scope.$parent.modal.close();
		};
	});

})();

