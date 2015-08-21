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
		var actions = [{"dummy_action" : {
		    "id": "dummy_action",
		    "description": "Do Nothing",
		    "resourcesType": "DummyObj",
		    "hasConditionHandlers": true,
		    "resourcesMode": "Many",
		    "neededAuthorithies": [],
		    "context": "plataforma"
		}}, {"do_nothing" : {
		    "id": "do_nothing",
		    "description": "Do Nothing",
		    "resourcesType": "Long",
		    "hasConditionHandlers": false,
		    "resourcesMode": "None",
		    "neededAuthorithies": [],
		    "context": "plataforma"
		}}, {"do_nothing_long": {
		    "id": "do_nothing_long",
		    "description": "Do Nothing Long",
		    "resourcesType": "Long",
		    "hasConditionHandlers": false,
		    "resourcesMode": "One",
		    "neededAuthorithies": ["peticionador"]
		}}];
		
		var resources = [{attr : "TESTE"}];

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($compile, $rootScope, $httpBackend, properties, $window) {
			compile = $compile;
			scope = $rootScope.$new();
			scope.resources = resources;
			$httpBackend.expectGET(properties.baseUrl + '/actions')
				.respond(actions);
			$window.sessionStorage.setItem('papel', {nome : "peticionador"});
		}));

		it('Deveria apresentar uma lista de ações', function() {
			var element = compile('<actions resources="resources" type="DummyObj" />')(scope);
			expect(element(by.css("dropdown-menu")).length).toMatch(1);
		});
	});
})();
