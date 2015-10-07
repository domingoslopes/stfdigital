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
		var DashletsProvider;
		var Dashlets;

		beforeEach(module('appDev'));
		
		beforeEach(function() {
			var fakeModule = angular.module('appDev.fake', ['appDev']);
			fakeModule.config(function(_DashletsProvider_) {
				DashletsProvider = _DashletsProvider_;
			});
			
			module('appDev.fake');
			
			inject(function(_Dashlets_) {
				Dashlets = _Dashlets_;
			});
		});
		
		describe('Com configuração básica', function() {
			it('Deveria estar definido', function() {
				expect(DashletsProvider).not.toBeUndefined();
			});
			
			it('Deveria ter configurado o dashlet', function() {
				DashletsProvider.dashlet('dashlet-01', {
					view: 'view-01',
					controller: 'Controller01'
				});
				
				expect(Dashlets.getDashletView('dashlet-01')).toBe('view-01');
				expect(Dashlets.getDashletController('dashlet-01')).toBe('Controller01');
			});
		});
		
		describe('Com configurações não realizadas sendo chamadas', function() {
			it('Deveria ter reclamado de configuração inválida', function() {
				DashletsProvider.dashlet('dashlet-01', {
					view: 'view-01',
					controller: 'Controller01'
				});
				
				expect(function() {
					Dashlets.getDashletView('dashlet-nao-configurada');
				}).toThrow(new Error('Dashlet dashlet-nao-configurada não foi encontrada.'));
				
				expect(function() {
					Dashlets.getDashletController('dashlet-nao-configurada');
				}).toThrow(new Error('Dashlet dashlet-nao-configurada não foi encontrada.'));
			});
		});
	});
})();
