/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 04.12.2015
 */ 

/* jshint undef:false*/
(function() {
	'use strict';
	
	describe('Cadastro de Associado: Service', function() {
		var controller;
		var scope;
		var associadoService;
		var httpBackend;
		var properties;
		
		var handler = {
			sucesso: function() {},
			falha: function() {}
		};		
		
		var dadosAssociadoComCargoParaCadastro = {
			idOrgao: 1, 
			cpf: "54218811806", 
			nome: "FULANO SAYAJIN", 
			tipoAssociacao: "Gestor", 
			cargo: "Analista"
		};

		var dadosAssociadoSemCargoParaCadastro = {
			idOrgao: 1, 
			cpf: "55448211780", 
			nome: "CICRANO POLICARBONATO", 
			tipoAssociacao: "Representante"
		};

		var dadosAssociadoInvalidos = {
			cpf: "55448211780", 
			nome: "CICRANO POLICARBONATO", 
			tipoAssociacao: "Representante"
		};
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $httpBackend, _properties_, AssociadoService) {
			associadoService = AssociadoService;
			httpBackend = $httpBackend;
			properties = _properties_;
		})); 
		
		it("Deveria cadastrar um associado com cargo", function() {
			var sucessoLocal = spyOn(handler, 'sucesso');
			var falhaLocal = spyOn(handler, 'falha');
			
			httpBackend.expectPOST(properties.apiUrl + '/associado', dadosAssociadoComCargoParaCadastro).respond({});
			associadoService.cadastrar(dadosAssociadoComCargoParaCadastro).then(handler.sucesso, handler.falha);
			httpBackend.flush();

			expect(sucessoLocal).toHaveBeenCalled();
			expect(falhaLocal).not.toHaveBeenCalled();
		});
		
		it("Deveria cadastrar um associado sem cargo", function() {
			var sucessoLocal = spyOn(handler, 'sucesso');
			var falhaLocal = spyOn(handler, 'falha');
			
			httpBackend.expectPOST(properties.apiUrl + '/associado', dadosAssociadoSemCargoParaCadastro).respond({});
			associadoService.cadastrar(dadosAssociadoSemCargoParaCadastro).then(handler.sucesso, handler.falha);
			httpBackend.flush();
			
			expect(sucessoLocal).toHaveBeenCalled();
			expect(falhaLocal).not.toHaveBeenCalled();
		});
		
		it("Não deveria cadastrar um associado com dados inválidos", function() {
			var sucessoLocal = spyOn(handler, 'sucesso');
			var falhaLocal = spyOn(handler, 'falha');
			
			httpBackend.expectPOST(properties.apiUrl + '/associado', dadosAssociadoInvalidos).respond(400);
			associadoService.cadastrar(dadosAssociadoInvalidos).then(handler.sucesso, handler.falha);
			httpBackend.flush();
			
			expect(sucessoLocal).not.toHaveBeenCalled();
			expect(falhaLocal).toHaveBeenCalled();
		});
	});
})();