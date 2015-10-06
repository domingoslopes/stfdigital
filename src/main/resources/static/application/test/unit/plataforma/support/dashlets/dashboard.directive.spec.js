/**
 * Testes unitários da diretiva dashboard.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: Dashboard', function() {
		var $compile, $q, scope;
		
		var mockDashboardService = {
			getDashlets: function() {}
		};
		
		var mockDashlets = {
			getDashletController: function() {console.log('mockGetDashletController');},
			getDashletView: function() {console.log('mockGetDashletView');}
		};

		beforeEach(module('appDev'));
		
		beforeEach(module(function($provide) {
			$provide.value('DashboardService', mockDashboardService);
			$provide.value('Dashlets', mockDashlets);
		}));
		
		beforeEach(inject(function(_$compile_, $rootScope, _$q_) {
			$compile = _$compile_;
			$q = _$q_;
			scope = $rootScope.$new();
		}));

		beforeEach(function() {
			spyOn(mockDashboardService, 'getDashlets').and.returnValue($q.when(['dashlet-01']));
			spyOn(mockDashlets, 'getDashletController').and.returnValue('MinhasPeticoesDashletController');
			spyOn(mockDashlets, 'getDashletView').and.returnValue('application/autuacao/peticionamento/dashlets/peticoes.tpl.html');
		});
		
		it('Deveria ter compilado a diretiva', function() {
			var element = $compile('<div data-dashboard=""></div>')(scope);
			
			scope.$digest();
			// TODO Criar uma verificação desse conteúdo.
//			console.log(element);
		});

	});
})();
