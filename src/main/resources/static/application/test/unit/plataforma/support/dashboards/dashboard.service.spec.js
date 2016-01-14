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
			$httpBackend.expectGET(properties.apiUrl + '/dashboards').respond([{dashlets: [{nome : 'dashlet-01'}, {nome : 'dashlet-02'}]}]);
			
			DashboardService.getDashboards().then(function(dashboards) {
				expect(dashboards[0].dashlets).toEqual([{nome : 'dashlet-01'}, {nome : 'dashlet-02'}]);
			}, function() {
				fail();
			});
			$httpBackend.flush();
		});
	});
})();
