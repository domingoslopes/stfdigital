/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 12.02.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var Utils = require('./support');
	var path = require('path');
	var utils = new Utils();
	
	// Parametrização do caminho dos arquivos.
	var filesDirFullPath = browser.params.filesDirPath ? browser.params.filesDirPath : (__dirname + '/../files');
	
	var OrganizaPecasPage = function () {
		
		this.arrastarPecas = function(){
			browser.actions().dragAndDrop(element(by.css("#tabelaPecas tr:nth-child(1)")), element(by.css("#tabelaPecas tr:nth-child(3)"))).mouseDrown().perform();
		};
		
		this.executarAcaoJuntar = function() {
			element.all(by.repeater('peca in organiza.processo.pecas')).count().then(function(count) {
				  selecionaPeca(count - 1);
			});
			selecionaAcao(3);
			executarAcao('juntar-peca');
		};
		//qtdPecas representa a quantidade de peças que serão juntadas
		this.executarAcaoUnir = function(qtdPecas) {
			var i = 0;
			for (i ; i < qtdPecas ; i++){
				var peca = element(by.repeater('peca in organiza.processo.pecas').row(i));
				peca.element(by.css('input')).click();
				browser.waitForAngular();
			}
			
			selecionaAcao(0);
			executarAcao('unir-pecas');
		};
		
		this.recuperarTotalDePecas = function(){
			return element.all(by.repeater("peca in organiza.processo.pecas")).count();
		};
		
		this.recuperarTotalPaginas = function(){
			return element(by.css('[for=numeroPaginas]')).getAttribute("data-numero-total-paginas");
		}
		
		this.executarAcaoDividir = function(){
			selecionaPeca(1)
			selecionaAcao(0);
			browser.waitForAngular();
		}
		
		this.confirmarAcaoDividir = function(){
			executarAcao('dividir-peca');
		}
		
		this.executarAcaoExcluir = function() {
			selecionaPeca(1)
			selecionaAcao(2);
			executarAcao('excluir-pecas');
		};
		
		this.executarAcaoEditarPeca = function() {
			var peca = element(by.repeater('peca in organiza.processo.pecas').row(0));
			peca.element(by.css('input')).click();
			browser.waitForAngular();
			selecionaAcao(1);
		};
		
		this.confirmarOrganicaoPecas = function(){
			executarAcao('organizar-pecas');
		};
		
		this.confirmarOrganicaoPecas = function(){
			element(by.id('btn_exec_organizar-pecas')).click();
		};
		
		this.alteraNumeroOrdemPeca = function(numeroOrdem) {
			utils.substituirValor(element(by.id('sequencialPeca')), numeroOrdem);
		};
		
		this.alteraTipoPeca = function(tipoPeca) {
			utils.select('div#s2id_tipoPeca', tipoPeca);
		};
		
		this.alteraDescricaoPeca = function(descricao) {
			utils.substituirValor(element(by.id('descricaoPeca')), descricao);
		};
		
		this.alteraVisibilidadePeca = function(visibilidade) {
			utils.select('div#s2id_visibilidadeId', visibilidade);
		};
		
		this.salvarEdicaoPeca = function(){
			executarAcao('editar-peca');
		};
		
		this.setarPaginaInicialFinal = function(inicio, fim){
			element(by.id('intervaloInicialPagina')).sendKeys(inicio);
			element(by.id('intervaloFinalPagina')).sendKeys(fim);
		}
		
		this.adicionarPeca = function(){
			element(by.id('botaoAdicionarIntervalosPeca')).click();
		}
		
		this.acionarOpcaoInserirPecas = function () {
			element(by.id('btn_inserir-pecas')).click();
			browser.waitForAngular();
		};
		
		this.uploadPecas = function(){
			var fileToUpload = 'pdf-de-teste-assinado-02.pdf';
			var absolutePath = path.resolve(filesDirFullPath, fileToUpload);
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
		
		var hasClass = function (element, cls) {
		    return element.getAttribute('class').then(function (classes) {
		        return classes.split(' ').indexOf(cls) !== -1;
		    });
		};
		
		this.waitUploadFinished = function(index, timeout) {
			browser.wait(browser.isElementPresent(by.css('#tabPecas')));
			
			var uploadedRow = element.all(by.repeater('peca in pecas')).get(index);
			var finishedMark = uploadedRow.element(by.css('td.progress-row'));
			
			browser.wait(function() {
				return hasClass(finishedMark, 'upload-finished');
			}, (!timeout) ? 3000 : timeout).then(function() {
				browser.waitForAngular();
			});
		};
		
		this.removerPeca = function() {
			element(by.id('btnRemoverPeca')).click();
		};
		
		this.removerTodasPecas = function() {
			element(by.id('btnRemoverTodasPecas')).click();
		};
		
		this.setarDescricao = function(descricao, id) {
			var index = !id ? 0 : id;
			element(by.id('descricao-' + index)).sendKeys(descricao);
			browser.waitForAngular();
		};
		
		this.selecionarTipoPeca = function(tipoPeca, id) {
			var index = !id ? 0 : id;
			utils.select('div#s2id_tipoPecaId-' + index, tipoPeca);
			browser.waitForAngular();
		};
		
		this.selecionarVisibilidadePeca = function(visibilidade, id) {
			var index = !id ? 0 : id;
			utils.select('div#s2id_visibilidade-' + index, visibilidade);
			browser.waitForAngular();
		};
		
		this.finalizar = function() {
			element(by.id('btn_exec_organizar-pecas')).click();
		};
		
		this.executarInsercaoPecas = function(){
			executarAcao('inserir-pecas');
		};
		
		var selecionaPeca = function(indicePecas){
			var peca = element(by.repeater('peca in organiza.processo.pecas').row(indicePecas));
			peca.element(by.css('input')).click();
			browser.waitForAngular();
		};
		
		var selecionaAcao = function(indiceAcao){
			var acao = element(by.css('actions'));
			acao.element(by.css('a.dropdown-toggle')).click();
			acao.element(by.repeater('action in actions').row(indiceAcao)).click();
			browser.waitForAngular();
			var modal = by.id('actionModal');
			browser.wait(browser.isElementPresent(modal));
			browser.wait(function() {
				return element(modal).isDisplayed();
			});
		};
		
		var executarAcao = function(acao) {
			var btn = by.id('btn_exec_' + acao);
			browser.wait(browser.isElementPresent(btn));
			browser.wait(function() {
				return element(btn).isDisplayed();
			});
			element(btn).click();
			browser.waitForAngular();
		};
	};

	module.exports = OrganizaPecasPage;
	
})();
