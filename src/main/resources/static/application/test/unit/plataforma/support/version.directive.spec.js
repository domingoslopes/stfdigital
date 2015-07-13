/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 08.07.2015
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: Version', function() {

		var compile;
		var rootScope;

		beforeEach(module('plataforma'));
		
		beforeEach(function() {
			module(function($provide) {
				// Mocando o valor...
				$provide.value('version', 'test');
			});
		});

		beforeEach(inject(function($compile, $rootScope) {
			compile = $compile;
			rootScope = $rootScope;
		}));

		it('Deveia apresentar o número da versão', function() {
			var element = compile('<div app-version></div>')(rootScope);
			expect(element.html()).toMatch(/test/i);
		});

	});
})();
