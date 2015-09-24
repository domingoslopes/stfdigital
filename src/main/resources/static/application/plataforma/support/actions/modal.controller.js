/**
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	angular.plataforma.controller('ModalActionController', function ($stateParams, $element, $previousState) {
		var modal = this;
		modal.action = $stateParams.action;
		$previousState.memo('modalInvoker'); 
		$element.modal({backdrop : 'static'});
		
		modal.close = function() {
			$element.modal('toggle');
			$('.modal-backdrop').remove();
			$element.remove();
			$previousState.go('modalInvoker');
		};
	});

})();

