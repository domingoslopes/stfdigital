/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: Actions', function() {
		var compile;
		var scope;
		var resources = [{attr : "TESTE"}];

		beforeEach(module('appDev'));
		
		inject(function($compile, $rootScope, $httpBackend, properties, $window) {
			compile = $compile;
			scope = $rootScope.$new();
			//scope.resources = resources;
			/*$httpBackend.expectGET(properties.apiUrl + '/actions')
				.respond(actions);*/
			//$window.sessionStorage.setItem('papel', {nome : "peticionador"});
		});

		it('Deveria apresentar uma lista de ações', function() {
			//var element = compile('<actions resources="resources" type="DummyObj" />')(scope);
			//expect(element(by.css("dropdown-menu")).length).toMatch(1);
		});
	});
})();
