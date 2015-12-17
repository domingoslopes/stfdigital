/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 16.12.2015
 */ 

/* jshint undef:false*/
(function() {
	'use strict';

	describe('Controlador de Permissões', function() {
		var scope;
		var controller;
		
		var usuarioLogadoMock = {
			id: 2,
			login: "usuario.logado",
			nome: "Usuário Logado",
			setorLotacao: {
				codigo: 600000627,
				nome: "SECRETARIA JUDICIÁRIA",
				sigla: "SEJ"
			},
			papeis: [
			    {
			    	id: 8,
			    	nome: "gestor-autuacao"
			    }
			],
			grupos: [
	            {
	            	id: 1,
	            	nome: "usuario",
	            	tipo: "CONFIGURACAO"
	            },
	            {
	            	id: 2,
	            	nome: "SEJ",
	            	tipo: "SETOR"
	            }
	        ]	
		};
		
		var usuarioMock = {
			id: 1,
			login: "usuario.mock",
			nome: "Usuário Mock",
			setorLotacao: null,
			papeis: [
			    {
			    	id: 1,
			    	nome: "peticionador"
			    },
			    {
			    	id: 4,
			    	nome: "preautuador",
			    	grupo: 2
			    }
			],
			grupos: [
	            {
	            	id: 1,
	            	nome: "usuario",
	            	tipo: "CONFIGURACAO"
	            },
	            {
	            	id: 2,
	            	nome: "SEJ",
	            	tipo: "SETOR"
	            }
	        ]
		};
		
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
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function($rootScope, $controller, $httpBackend, properties, SecurityService, AcessosService) {
			scope = $rootScope.$new();
			
			$httpBackend.expectGET(properties.apiUrl + '/acessos/papeis').respond(papeisMock);
			$httpBackend.expectGET(properties.apiUrl + '/acessos/grupos').respond(gruposMock);
			
			spyOn(SecurityService, 'user').and.returnValue(usuarioLogadoMock);
			
			controller = $controller('PermissoesController', {
				$scope: scope,
				SecurityService: SecurityService,
				AcessosService: AcessosService
			});
			
			$httpBackend.flush();
		}));
		
		it("Deveria instanciar corretamente o controlador", function() {
			expect(controller).not.toEqual(null);
		});
		
		it("Deveria, ao selecionar um usuário, seguir para o passo 2", function() {
			controller.selecionarUsuario(usuarioMock);
			expect(controller.passo).toEqual(2);
		});
		
		it("Deveria, ao limpar a seleção de usuário, voltar ao passo 1", function() {
			controller.selecionarUsuario(null);
			expect(controller.passo).toEqual(1);
		});
	});
})();