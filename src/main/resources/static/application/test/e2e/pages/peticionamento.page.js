/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 05.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	var path = require('path');
	
	var utils = new Utils();
	
	var PeticionamentoPage = function () {
		
		this.classificarClasse = function(sigla) {
			utils.select('div#s2id_classe', sigla);
		};
		
		this.classificarOrgao = function(nome) {
			utils.select('div#s2id_orgao', nome);
		};
		
		this.partePoloAtivo = function(nome) {
		    element(by.id('partePoloAtivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloAtivo')).click();
		};
		
		this.partePoloPassivo = function(nome) {
		    element(by.id('partePoloPassivo')).sendKeys(nome);
		    
			element(by.id('botaoAdicionarPartePoloPassivo')).click();
		};
		
		this.uploadPecas = function(){
			var fileToUpload = '../files/pdf-de-teste-assinado-02.pdf';
			var absolutePath = path.resolve(__dirname, fileToUpload);
			// Procura o elemento input da tela com o atributo com o tipo "file"
		    var fileElem = element(by.css('input[type="file"]'));

		    // É necessario dar visibilidade ao componente input
		    browser.executeScript(
		      "arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
		      fileElem.getWebElement());

		    // Envia o caminho e o arquivo para o input fazer a submissão. Não é necessário clicar no botão
		    fileElem.sendKeys(absolutePath);
		    browser.sleep(2000);
		    browser.waitForAngular();
		};
		
		this.removePecas = function() {
			element(by.id('btnRemovePecas')).click();
		};
		
		this.selecionarTipoPeca = function(descricao) {
			 utils.select('div#s2id_tipoPecaId', descricao);
		};
		
		this.registrar = function () {
			element(by.id('botaoPeticionar')).click();
		};
		
		this.registrarValorPeticao = function(){
			var elm = element(by.model("idPeticao"));
			elm.getAttribute('value').then(function (value) {
				console.log(value);
			   return value
			});
		};
		
	};

	module.exports = PeticionamentoPage;
	
})();
