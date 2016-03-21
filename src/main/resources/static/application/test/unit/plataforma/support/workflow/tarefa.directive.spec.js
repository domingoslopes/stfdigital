/**
 * @author Lucas Rodrigues
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: TarefaActions', function() {
		var rootScope, compile, tarefaService;
		
		var tarefa = {responsavel: 'Chapolin', dono: true};

		beforeEach(module('appDev'));

		beforeEach(inject(function($rootScope, $compile, $q, TarefaService) {
			rootScope = $rootScope;
			compile = $compile;
			tarefaService = TarefaService;
						
			tarefaService.consultarPorProcesso = function (processo) {
				return $q(function(res, rej) {
					res(tarefa);
				});
			};
			
			tarefaService.consultarPorProcessos = function (processos) {
				return $q(function(res, rej) {
					res([tarefa, tarefa]);
				});
			};
			
		}));
		
		it('Deveria compilar a diretiva de tarefa para um processo', function() {
			var scope = rootScope.$new();
			var element = angular.element('<tarefa-actions processo="6" tarefa="tarefa" />');

			var template = compile(element)(scope);
			scope.$digest();
			var iScope = template.isolateScope();
			
			expect(template[0].outerHTML).not.toBeNull();
			expect(iScope.tarefa).toEqual(tarefa);
		});
		
		it('Deveria compilar a diretiva de tarefa para vários processos', function() {
			var scope = rootScope.$new();
			var element = angular.element('<tarefa-actions processo="[6, 7]" tarefa="tarefa" />');

			var template = compile(element)(scope);
			scope.$digest();
			var iScope = template.isolateScope();
			
			expect(template[0].outerHTML).not.toBeNull();
			expect(iScope.tarefa).toEqual(tarefa);
		});

	});
})();
