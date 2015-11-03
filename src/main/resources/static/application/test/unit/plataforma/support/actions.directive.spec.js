/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';
	
	var actions = [{
	    id: "dummy_action",
	    description: "Dummy Action",
	    resourcesType: "DummyObj",
	    hasConditionHandlers: true,
	    resourcesMode: "Many",
	    neededAuthorities: ["peticionador"]
	}];

	describe('Directive: Actions', function() {
		var scope;
		var template;
		var element;
		var resources = [{attr : "TESTE"}];

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($compile, $rootScope, $state, $window, $q, ActionService) {
			scope = $rootScope.$new();
			scope.resources = resources;
			$window.sessionStorage.setItem('papel', JSON.stringify({nome : "peticionador"}));
			element = angular.element('<actions resources="resources" type="DummyObj" />');
			ActionService.list = function () {
				return $q(function(res, rej) {
					res(actions);
				});
			};
			template = $compile(element)(scope);
			scope.$digest();
		}));

		it('Deveria apresentar um botão dropdown', function() {
			expect(template[0].outerHTML).toContain('dropdown');
		});
		
		it('Deveria apresentar uma lista de ações', function() {
			var iScope = template.isolateScope();
			expect(iScope.actions.length).toBe(1);
		});
	});
	
	describe('Directive: Action', function() {
		var scope;
		var template;
		var element;
		var actionService;
		var resources = [{attr : "TESTE"}];

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($compile, $rootScope, $state, $q, $window, ActionService) {
			scope = $rootScope.$new();
			scope.resources = resources;
			$window.sessionStorage.setItem('papel', JSON.stringify({nome : "peticionador"}));
			element = angular.element('<action id="dummy_action" resources="resources" />');
			template = $compile(element)(scope);
			actionService = ActionService;
			ActionService.get = function(actionId) {
				return $q(function(res, rej) {
					res(actions[0]);
				});
			};
			actionService.isAllowed = function () {
				return $q(function(res, rej) {
					res(true);
				});
			};
			scope.$digest();
		}));

		it('Deveria apresentar um botão de ação', function() {
			expect(template[0].outerHTML).toContain('btn btn-default');
		});
		
		it('Deveria apresentar uma ação', function() {
			var iScope = template.isolateScope();
			expect(iScope.disabled).toBeFalsy();
		});
		
		it('Deveria desabilitar uma ação', function() {
			var iScope = template.isolateScope();
			iScope.disabled = true;
			expect(template[0].outerHTML).toContain('disabled="disabled"');
		});
	});
	
	describe('Directive: ActionExecutor', function() {
		var scope;
		var template;
		var element;
		var actionService;
		var resources = [{attr : "TESTE"}];

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($compile, $rootScope, $state, $q, $window, ActionService) {
			scope = $rootScope.$new();
			scope.resources = resources;
			$window.sessionStorage.setItem('papel', JSON.stringify({nome : "peticionador"}));
			element = angular.element('<action-executor id="dummy_action" resources="resources" btn-class="btn btn-success" ' +
				'icon-class="fa fa-hand-peace-o" description="Finalizar" show-description="true" ' + 
				'verify-if-allowed="true" show-not-allowed="false" />');
			template = $compile(element)(scope);
			actionService = ActionService;
			actionService.get = function(actionId) {
				return $q(function(res, rej) {
					res(actions[0]);
				});
			};
			actionService.isAllowed = function () {
				return $q(function(res, rej) {
					res(true);
				});
			};
			actionService.isValidResources = function(action, resources) {
				return true;
			};
			scope.$digest();
		}));

		it('Deveria apresentar um botão de execução', function() {
			expect(template[0].outerHTML).toContain('btn btn-success');
			var iScope = template.isolateScope();
			expect(iScope.disabled).toBeFalsy();
		});
		
		it('Deveria desabilitar um botão de execução', function() {
			var iScope = template.isolateScope();
			iScope.disabled = true;
			expect(template[0].outerHTML).toContain('disabled="disabled"');
		});
		
		it('Deveria executar uma ação', function() {
			var iScope = template.isolateScope();
			iScope.disabled = true;
			spyOn(actionService, 'execute').and.callFake(function() {
				return { then : function(cb) {return cb(null); } };
			});
			iScope.execute();
			expect(actionService.execute).toHaveBeenCalledWith('dummy_action', resources);
		});
	});
})();
