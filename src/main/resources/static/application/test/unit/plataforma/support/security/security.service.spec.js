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
			"setorLotacao": "CPIN"
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
		
	});
})();
