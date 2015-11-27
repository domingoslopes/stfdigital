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
			SecurityService.mockUser('peticionador');
			scope = $rootScope.$new();
			
			fakePesquisaService = {
				pesquisar: function(){
				}
			};
			
			spyOn(fakePesquisaService, 'pesquisar').and.returnValue($q.when({data:[{descricao : 'Petição #00001'},{descricao : 'Petição #00002'}]}));
			
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
				campos : ['identificacao', 'dataCadastramento', 'classeProcessual.sigla', 'numero', 'classeSugerida.sigla'],
				ordenadores: {identificacao: "ASC"}});
			expect(scope.peticoes[0].descricao).toEqual('Petição #00001');
			expect(scope.peticoes[1].descricao).toEqual('Petição #00002');
			expect(scope.peticoes.length).toEqual(2);
		});
	});
})();
