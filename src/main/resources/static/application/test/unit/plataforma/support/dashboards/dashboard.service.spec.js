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

	describe('Service: Dashboard', function() {
		var DashboardService;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_DashboardService_) {
			DashboardService = _DashboardService_;
		}));
		
		it('Deveria ter carregado as dashlets', function() {
			DashboardService.getDashlets().then(function(dashlets) {
				expect(dashlets).toBe(['minhas-peticoes']);
			});
		});
	});
})();
