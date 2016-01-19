/**
 * Realiza operações que resultam na inclusão de vários documentos.
 * 
 * @author Tomas.Godoi
 * @since 2016-01-19
 */
/* jshint undef:false */
(function() {
	'use strict';

	var externalDir = '../e2e';
	
	var LoginPage = require(externalDir + '/pages/login.page');

	var PrincipalPage = require(externalDir + '/pages/principal.page');

	var PeticionamentoPage = require('./pages/peticionamento.page.extension');

	var principalPage;

	var loginPage;

	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage)
			loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};

	describe('Autuação de Petições Digitais Originárias:', function() {

		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});

		it('Deveria logar como peticionador', function() {
			login('peticionador');
		});

		it('Deveria navegar para a página de envio de petições digitais', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();

			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);

			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso('link_registrar-peticao-eletronica');

			// Verificando se, após iniciar o processo, o browser está na página de registro de
			// petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao/);
		});

		for (var k = 0; k < 10; k++) {
			it('Deveria enviar uma nova petição digital ' + (k + 1), function() {
				peticionar('RE', 10, 'uploadPeca1MB');
				principalPage.iniciarProcesso('link_registrar-peticao-eletronica');
			});
		}
		
		it('Deveria fazer logout', function() {
			loginPage.logout();
		});

		var peticionar = function(siglaClasse, qtdePecas, uploadMethodName) {

			var peticionamentoPage = new PeticionamentoPage();

			peticionamentoPage.classificarClasse(siglaClasse);

			peticionamentoPage.partePoloAtivo('João da Silva');

			peticionamentoPage.partePoloPassivo('Maria da Silva');

			var i;
			for (i = 0; i < qtdePecas; i++) {
				peticionamentoPage[uploadMethodName]();
			}
			
			for (i = 0; i < qtdePecas; i++) {
				peticionamentoPage.selecionarTipoPeca('Ato coator', i);
			}

			for (i = 0; i < qtdePecas; i++) {
				peticionamentoPage.waitUploadFinished(i);
			}
			
			peticionamentoPage.registrar('registrar-peticao-eletronica');
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		};

	});
})();