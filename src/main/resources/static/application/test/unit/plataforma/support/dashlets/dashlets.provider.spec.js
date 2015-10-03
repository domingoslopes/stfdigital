/**
 * Testes unitários do provider Dashlets.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Provider: Dashlets', function() {
		var dashletsProvider;
		var dashlets;

		beforeEach(module('appDev'));
		
		beforeEach(function() {
			var fakeModule = angular.module('appDev.fake', ['appDev']);
			fakeModule.config(function(_dashletsProvider_) {
				dashletsProvider = _dashletsProvider_;
			});
			
			module('appDev.fake');
			
			inject(function(_dashlets_) {
				dashlets = _dashlets_;
			});
		});
		
		describe('Com configuração básica', function() {
			it('Deveria estar definido', function() {
				expect(dashletsProvider).not.toBeUndefined();
			});
			
			it('Deveria ter configurado o dashlet', function() {
				dashletsProvider.dashlet('dashlet-01', {
					view: 'view-01',
					controller: 'Controller01'
				});
				
				expect(dashlets.getDashletView('dashlet-01')).toBe('view-01');
				expect(dashlets.getDashletController('dashlet-01')).toBe('Controller01');
			});
			
			it('Deveria ter configurado o defaultTemplate', function() {
				dashletsProvider.defaultTemplate('template-01');
				
				expect(dashlets.getDefaultDashletTemplate()).toBe('template-01');
			});
		});
		
		describe('Com configurações não realizadas sendo chamadas', function() {
			it('Deveria ter reclamado de configuração inválida', function() {
				dashletsProvider.defaultTemplate('template-01').dashlet('dashlet-01', {
					view: 'view-01',
					controller: 'Controller01'
				});
				
				expect(function() {
					dashlets.getDashletView('dashlet-nao-configurada');
				}).toThrow(new Error('Dashlet dashlet-nao-configurada não foi encontrada.'));
				
				expect(function() {
					dashlets.getDashletController('dashlet-nao-configurada');
				}).toThrow(new Error('Dashlet dashlet-nao-configurada não foi encontrada.'));
			});
		});
	});
})();
