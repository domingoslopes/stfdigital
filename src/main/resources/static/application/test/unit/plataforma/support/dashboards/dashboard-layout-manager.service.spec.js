/**
 * Testes unitários do service DashboardLayoutManager.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Service: DashboardLayoutManager', function() {
		var DashboardLayoutManager;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_DashboardLayoutManager_) {
			DashboardLayoutManager = _DashboardLayoutManager_;
		}));
		
		it('Deveria ter retornado um layout tabular de duas colunas corretamente', function() {
			var layout = DashboardLayoutManager.defaultLayout(["dashlet-01", "dashlet-02", "dashlet-03"]);
			
			expect(layout.linhas.length).toBe(2);
			expect(layout.linhas[0].colunas.length).toBe(2);
			expect(layout.linhas[1].colunas.length).toBe(1);
			
			expect(layout.linhas[0].colunas[0].dashlet).toBe('dashlet-01');
			expect(layout.linhas[0].colunas[1].dashlet).toBe('dashlet-02');
			expect(layout.linhas[1].colunas[0].dashlet).toBe('dashlet-03');
		});
		
		it('Deveria ter retornado um layout sem nenhum dashlet corretamente', function() {
			var layout = DashboardLayoutManager.defaultLayout([]);
			
			expect(layout.linhas.length).toBe(0);
		});
		
	});
})();
