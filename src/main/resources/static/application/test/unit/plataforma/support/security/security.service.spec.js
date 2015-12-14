/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Service: SecurityService', function() {
		var rootScope, securityService, httpBackend;
		var desativarMockUsuario = false;
		
		var usuario = {
			"id": 12,
			"login": "gestor-cadastro",
			"nome": "gestor-cadastro",
			"setorLotacao": "CPIN",
			"papeis": [
	            {
	            	"id": 10,
	            	"nome": "gestor-cadastro",
	            	"setor": ""
        		}
            ],
            
            "authorities": [
                {
                	"authority": "VISUALIZAR_NOTIFICACAO"
        		},
        		{
        			"authority": "CRIAR_ASSOCIADO"
				},
				{
					"authority": "CRIAR_NOTIFICACAO"
				}
			]
		};

		beforeEach(module('appDev'));
		
		beforeEach(inject(function(SecurityService) {
			desativarMockUsuario = false;
			securityService = SecurityService;
			
			spyOn(securityService, 'user').and.callFake(function(params) {
				if (desativarMockUsuario) {
					return null;
				} else {
					return usuario;
				}
			});
		}));
		
		it('Deveria carregar o serviço', function() {
			expect(securityService).not.toEqual(null);
		});
		
		
		it('Deveria retornar falso quando um usuário não está logado', function() {
			desativarMockUsuario = true;
			expect(securityService.hasPapel("gestor-cadastro")).toBeFalsy();
		});
		
		it('Deveria verificar se um usuário possui as permissões corretas', function() {
			expect(securityService.hasPapel("gestor-cadastro")).toBeTruthy();
			expect(securityService.hasPapel("sayajin")).toBeFalsy();
		});
	});
})();
