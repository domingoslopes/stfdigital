/**
 * @author Tomas.Godoi
 */
/*jshint undef:false */
(function() {
	'use strict';

	var PeticionamentoPage = require('../../e2e/pages/peticionamento.page');
	
	var path = require('path');
	
	var uploadFiles = {
		'100k': '../files/Doc002-083726B-Assinado-A1.pdf',
		'1MB': '../files/Doc001-1048385B-Assinado-01.pdf',
		'10MB': '../files/Doc001-10286212B-Assinado-01.pdf',
		'100MB': '/Arquivos/Doc001-104365992B.pdf',
	};
	
	var PeticionamentoPageForBenchmark = function () {
		PeticionamentoPage.call(this);
	};
	
	var uploadPecas = function(fileToUpload){
		var absolutePath = path.resolve(__dirname, fileToUpload);
		// Procura o elemento input da tela com o atributo com o tipo "file"
	    var fileElem = element(by.css('input[type="file"]'));

	    // É necessario dar visibilidade ao componente input
	    browser.executeScript(
	      "arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
	      fileElem.getWebElement());

	    // Envia o caminho e o arquivo para o input fazer a submissão. Não é necessário clicar no botão
	    fileElem.sendKeys(absolutePath);
	    browser.waitForAngular();
	};
	
	PeticionamentoPageForBenchmark.prototype.uploadPecaDeTamanho = function(size) {
		uploadPecas(uploadFiles[size]);
	};
	
	PeticionamentoPageForBenchmark.prototype.waitGoToDashboard = function() {
		browser.wait(function() {
			return browser.getCurrentUrl().then(function(url) {
				return /dashboard/.test(url);
			});
		}, 60000);
	};
	
	module.exports = PeticionamentoPageForBenchmark;
	
})();
