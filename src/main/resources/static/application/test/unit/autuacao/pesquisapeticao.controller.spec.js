/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.00
 * @since 01.09.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Pesquisa Petição Controller', function() {
		var controller;
		var scope;
		var pesquisaService;
		var messagesService;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, messages, ClasseService, PesquisaService) {
			
			scope = $rootScope.$new(); 
			pesquisaService = PesquisaService;
			messagesService = messages;
			
			controller = $controller('PesquisaPeticaoController', {
				$scope : scope, 
				messages : messages,
				classes : [], 
				PesquisaService : PesquisaService
			});
		}));
		
		it ('Deveria carregar o controlador', function() {
			expect(controller).not.toBeNull();
		});
		
		it ('Deveria aprensentar erro em uma pesquisa sem filtros', function() {
			spyOn(messagesService, 'error');
			scope.pesquisar();
			
			expect(messagesService.error).toHaveBeenCalled();
		});
		
		it ('Deveria realizar uma pesquisa', function() {
			scope.classe = 'ADI';
			var command = { 
				indices : ['autuacao'],
				ordenadores : {'identificacao' : 'ASC'},
				filtros : {'classeSugerida.sigla' : [scope.classe]}
			};
			spyOn(pesquisaService, 'pesquisar').and.callFake(function() {
				return { then : function(cb) {return cb({data : ['teste']}); } };
			});
			scope.pesquisar();
			
			expect(pesquisaService.pesquisar).toHaveBeenCalledWith(command);
			expect(scope.resultados).toEqual(['teste']);
		});
	});
})();