/**
 * Testes unitários das diretivas dashboard e dashlet.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: Dashboard', function() {
		var $compile, $q, scope, $compileProvider;
		
		var mockDashboardService = {
			getDashlets: function() {}
		};

		beforeEach(module('appDev'));
		
		beforeEach(module(function($provide, _$compileProvider_) {
			$provide.value('DashboardService', mockDashboardService);
			$compileProvider = _$compileProvider_;
		}));
		
		beforeEach(inject(function(_$compile_, $rootScope, _$q_) {
			$compile = _$compile_;
			$q = _$q_;
			scope = $rootScope.$new();
		}));

		beforeEach(function() {
			spyOn(mockDashboardService, 'getDashlets').and.returnValue($q.when(['dashlet-01', 'dashlet-02']));
		});
		
		it('Deveria ter compilado a diretiva', function() {
			var dashletsNames = [];
			// Sobreescrevendo a diretiva interna utilizada.
			$compileProvider.directive('dashlet', function() {
				return {
					restrict : 'EA',
					priority: 9999,
					scope: {
						dashlet: '='
					},
					terminal: true,
					template: '',
					link: function(scope, element, attrs) {
						dashletsNames.push(scope.dashlet);
					}
				};
			});
			
			var element = $compile('<div data-dashboard=""></div>')(scope);
			
			scope.$digest();
			
			expect(dashletsNames).toEqual(['dashlet-01', 'dashlet-02']);
		});

	});
	
	describe('Directive: Dashlets', function() {
		var $compile, $q, $templateCache, $controllerProvider, $timeout, scope;
		
		var mockDashlets = {
			getDashletController: function() {},
			getDashletView: function() {}
		};

		beforeEach(module('appDev'));
		
		beforeEach(module(function($provide, _$controllerProvider_) {
			$provide.value('Dashlets', mockDashlets);
			$controllerProvider = _$controllerProvider_;
		}));
		
		beforeEach(inject(function(_$compile_, $rootScope, _$q_, _$templateCache_, _$timeout_) {
			$compile = _$compile_;
			$q = _$q_;
			scope = $rootScope.$new();
			$templateCache = _$templateCache_;
			$timeout = _$timeout_;
		}));

		beforeEach(function() {
			spyOn(mockDashlets, 'getDashletController').and.returnValue('FakeDashlet01Controller');
			spyOn(mockDashlets, 'getDashletView').and.returnValue('fake/fake-dashlet01-view.tpl.html');
			$templateCache.put('fake/fake-dashlet01-view.tpl.html', '<div class="fake-dashlet-01-view">{{fakeVariable}}</div>');
			$controllerProvider.register('FakeDashlet01Controller', function($scope) {
				$scope.fakeVariable = 'fake-01';
			});
		});
		
		it('Deveria ter compilado a diretiva', function() {
			var element = $compile('<div data-dashlet="dashlet-01"></div>')(scope);
			
			scope.$digest();
			$timeout.flush(1);
			var item = element.find('div.fake-dashlet-01-view');
			expect(item.length).toBe(1);
			expect(item.text()).toBe('fake-01');
		});

	});
	
})();
