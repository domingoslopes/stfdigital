/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 16.12.2015
 */ 

/* jshint undef:false*/
(function() {
	'use strict';

	describe('Service de Acessos', function() {
		var acessosService;
		var httpBackend;
		var properties;
		
		var papeisMock = [
            {id: 1, nome: "peticionador"},
            {id: 2, nome: "recebedor"},
            {id: 3, nome: "representante"},
            {id: 4, nome: "preatutuador", grupo: "SEJ"}
		];
		
		var gruposMock = [
			{id: 1, nome: "usuario", tipo: "CONFIGURACAO"},
			{id: 2, nome: "SEJ", tipo: "SETOR"}
		];
		
		var papeisUserMock = [
              {id: 1, nome: "peticionador"},
  		];
		
		var gruposUserMock = [
			{id: 1, nome: "usuario", tipo: "CONFIGURACAO"},
			{id: 2, nome: "SEJ", tipo: "SETOR"}
		];
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function($httpBackend, _properties_, AcessosService) {
			httpBackend = $httpBackend;
			properties = _properties_;
			acessosService = AcessosService;
		}));
		
		it("Deveria listar corretamente os papeis do sistema", function() {
			httpBackend.expectGET(properties.apiUrl + '/acessos/papeis').respond(papeisMock);
			acessosService.papeis().then(function(response) {
				expect(response.data).toEqual(papeisMock);
			});
			httpBackend.flush();
		});
		
		it("Deveria listar corretamente os grupos do sistema", function() {
			httpBackend.expectGET(properties.apiUrl + '/acessos/grupos').respond(gruposMock);
			acessosService.grupos().then(function(response) {
				expect(response.data).toEqual(gruposMock);
			}); 
			httpBackend.flush();
		});
		
		it("Deveria listar corretamente os papeis de um usuário", function() {
			httpBackend.expectGET(properties.apiUrl + '/acessos/usuarios/papeis', {params: {login: "usuario.mock"}}).respond(papeisUserMock);
			acessosService.papeis('usuario.mock').then(function(response) {
				expect(response.data).toEqual(papeisUserMock);
			});
			httpBackend.flush();
		});
		
		it("Deveria listar corretamente os grupos de um usuário", function() {
			httpBackend.expectGET(properties.apiUrl + '/acessos/usuarios/grupos', {params: {login: "usuario.mock"}}).respond(gruposUserMock);
			acessosService.grupos('usuario.mock').then(function(response) {
				expect(response.data).toEqual(gruposUserMock);
			});
			httpBackend.flush();
		});
	});
})();