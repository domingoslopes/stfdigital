/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Dashlet Minhas Petições: Controller', function() {
		var controller;
		var scope;
		var fakePesquisaService;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $q, SecurityService) {
			SecurityService.mockUser({name: 'peticionador'});
			scope = $rootScope.$new();
			
			fakePesquisaService = {
				pesquisar: function(){
				}
			};
			
			spyOn(fakePesquisaService, 'pesquisar').and.returnValue($q.when({data:[{objeto : { 'identificacao' : 'Petição #00001', 'peticao.sequencial' : 1 }},{objeto : { 'identificacao' : 'Petição #00002', 'peticao.sequencial' : 2 }}]}));
			
			controller = $controller('MinhasPeticoesDashletController', {
				$scope : scope,
				PesquisaService : fakePesquisaService
			});
		}));
		
		it('Deveria instanciar o controlador do dashlet de minhas tarefas', function() {
			expect(controller).not.toEqual(null);
		});

		it('Deveria carregar a lista de petições no escopo do controlador', function() {
			scope.$apply();
			expect(fakePesquisaService.pesquisar).toHaveBeenCalledWith({
				filtros: {usuarioCadastramento: ["peticionador"]},
				indices: ["autuacao"],
				campos : ['identificacao', 'dataCadastramento'],
				ordenadores: {identificacao: "ASC"}});
		});
	});
})();
