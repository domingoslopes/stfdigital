/**
 * TODO Documentar
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: Dashboard', function() {
		var rootScope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $window) {
			rootScope = $rootScope;
		}));
		
		it('Deveria ter executado o teste', function() {
			expect(true).toBe(true);
		});
		it('Deveria ter executado o teste2', function() {
			expect(true).toBe(true);
		});

	});
})();
