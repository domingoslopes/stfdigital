/**
 * Mocks específicos para os testes unitários.
 * 
 * @author Tomas.Godoi
 */ 
(function() {
	'use strict';

	var mocksModule = angular.module('unit.mocks', []);

	// Mock do serviço de segurança.
	var mockSecurityService = ['$delegate', function($delegate) {
		var currentUser = null;
		
		$delegate.mockUser = function(user) {
			currentUser = user;
		};
		
		$delegate.user = function() {
			return currentUser;
		};
		
		$delegate.logout = function() {
			// Faz nada
		};
		
		return $delegate;
	}];
	
	mocksModule.config(['$provide', function($provide) {
		$provide.decorator('SecurityService', mockSecurityService);
	}]);
})();