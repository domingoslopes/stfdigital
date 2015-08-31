/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Service: ActionService', function() {
		var rootScope, actionService, httpBackend;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $window, ActionService) {
			rootScope = $rootScope;
			actionService = ActionService;
			$window.sessionStorage.setItem('papel', JSON.stringify({nome : "peticionador"}));
		}));
		
		it('Deveria ter carregado as ações', function() {
			actionService.load("autuacao").then(function() {
				expect(actionService.get('dummy_action')).not.toBe(null);
			});
			rootScope.$apply();
		});

		it('Deveria retornar uma lista de ações sobre DummyObj', function() {
			actionService.load("autuacao").then(function() {
				actionService.list([{attr : "TESTE"}], "DummyObj", null)
					.then(function(actions) {
						expect(actions.length).toEqual(1);
					});
			});
			rootScope.$apply();
		});
		
		it('Deveria retornar uma lista de ações sobre Long', function() {
			actionService.load("autuacao").then(function() {
				actionService.list([1], "Long", null)
					.then(function(actions) {
						expect(actions.length).toEqual(1);
					});
			});
			rootScope.$apply();
		});
		
		it('Deveria permitir a ação', function() {
			actionService.load("autuacao").then(function() {
				actionService.isAllowed('dummy_action', [{attr : "TESTE"}])
					.then(function(result) {
						expect(result).toEqual(true);
					});
			});
			rootScope.$apply();
		});
		
		it('Deveria executar a ação', function() {
			actionService.load("autuacao").then(function() {
				actionService.execute('dummy_action', [{attr : "TESTE"}])
					.then(function(result) {
						expect(result).toBe(null);
					});
			});
			rootScope.$apply();
		});		
	});
})();
