/**
 * Testes unitários do service DashboardService.
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
		var $httpBackend;
		var properties;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_DashboardService_, _$httpBackend_, _properties_) {
			DashboardService = _DashboardService_;
			$httpBackend = _$httpBackend_;
			properties = _properties_;
		}));
		
		it('Deveria ter carregado as dashlets do dashboard padrão', function() {
			$httpBackend.expectGET(properties.apiUrl + '/dashboards/padrao').respond({'dashlets': ['dashlet-01', 'dashlet-02']});
			
			DashboardService.getDashlets().then(function(dashlets) {
				expect(dashlets).toEqual(['dashlet-01', 'dashlet-02']);
			}, function() {
				fail();
			});
			$httpBackend.flush();
		});
	});
})();
