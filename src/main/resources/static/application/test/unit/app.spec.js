/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Aplicação', function() {
		var module;
		var deps;

		var hasModule = function(m) {
			return deps.indexOf(m) >= 0;
		};

		beforeEach(function() {
			module = angular.module('app');
			deps = module.value('app').requires;
		});

		it('O módulo principal deve estar registrado', function() {
			expect(module).not.toEqual(null);
		});

		it('O mecanismo de rotas deve estar registrado', function() {
			expect(hasModule('ui.router')).toEqual(true);
		});

	});
})();
