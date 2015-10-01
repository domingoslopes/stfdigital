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

	describe('Service: Dashlets', function() {
		var rootScope, dashletsService;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, DashletsService) {
			rootScope = $rootScope;
			dashletsService = DashletsService;
		}));
		
		it('Deveria ter carregado as dashlets', function() {
			dashletsService.getDashlets().then(function(dashlets) {
				expect(dashlets).toBe(['minhas-peticoes']);
			});
		});
	});
})();
