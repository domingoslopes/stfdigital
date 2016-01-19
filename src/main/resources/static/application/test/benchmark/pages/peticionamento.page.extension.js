/**
 * @author Tomas.Godoi
 */
/*jshint undef:false */
(function() {
	'use strict';

	var PeticionamentoPage = require('../../e2e/pages/peticionamento.page');
	
	var path = require('path');
	
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
	
	PeticionamentoPageForBenchmark.prototype.uploadPeca1MB = function() {
		uploadPecas('../files/Doc001-1048385B-Assinado-01.pdf');
	};
	
	module.exports = PeticionamentoPageForBenchmark;
	
})();
