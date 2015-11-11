/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Dashlet Minhas Tarefas: Controller', function() {
		var controller;
		var scope;
		var fakePesquisaService;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $http, $window, $q, properties, TarefaService, PesquisaService) {
			$window.sessionStorage.papel = JSON.stringify('recebedor');
			scope = $rootScope.$new();
			
			fakePesquisaService = {
				pesquisar : function(){}
			}
			
			spyOn(fakePesquisaService, 'pesquisar').and.returnValue($q.when({tarefas: [{descricao : 'Petição #00001', metadado : {informacao : }}, {descricao : 'Petição #00002'}]}));
			
			controller = $controller('MinhasTarefasDashletController', {
				$scope : scope,
				PesquisaService : fakePesquisaService
			});
			
		}));

/*		it('Deveria instanciar o controlador do dashlet de minhas tarefas', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(scope.tarefas[0].descricao).toEqual('Petição #00001');
			expect(scope.tarefas[1].descricao).toEqual('Petição #00002');
			expect(scope.tarefas.length).toEqual(2);
		});
		*/
		
		
		
		it('Deveria instanciar o controlador do dashlet de minhas tarefas', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(fakePesquisaService.pesquisar).toHaveBeenCalledWith({
				filtros: { 'id.sequencial' : [metadado.informacao] },
				indices: ['autuacao', 'distribuicao'],
				campos : ['identificacao']
				//tipos : [metadado.tipoInformacao]
			});
			expect(scope.tarefas[0].descricao).toEqual('Petição #00001');
			expect(scope.tarefas[1].descricao).toEqual('Petição #00002');
			expect(scope.tarefas.length).toEqual(2);
		});
		
		
	});
})();
