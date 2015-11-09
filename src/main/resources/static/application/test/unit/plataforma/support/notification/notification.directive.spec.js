/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false */
(function() {
	'use strict';
	
	describe('Directive: Notification Center', function() {
		var $compile, $rootScope;
		var scope;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$compile_, _$rootScope_, NotificationService) {
			$compile = _$compile_;
			$rootScope = _$rootScope_;
			scope = $rootScope.$new();
			NotificationService = {
				registrarNotificacao : function() {},
				listarLidas : function() { return {} },
				listarNaoLidas: function () { return {}},
				marcarComoLida : function (){return {} }};
		}));
		
		it('Deveria compilar a diretiva', function() {
			var element = $compile('<div data-notification-center=""></div>')(scope);
			
			scope.$digest();
			
			expect(element).toBeDefined();
		});
		
	});
})();