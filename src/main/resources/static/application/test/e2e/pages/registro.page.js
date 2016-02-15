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
	
	var PrincipalPage = require('./principal.page');
	
	var utils = new Utils();
	
	var RegistroPage = function () {
		
		this.classificarTipoRecebimento = function (sigla) {
			utils.select('div#s2id_tipoRecebimento', sigla);
			browser.waitForAngular();
		};
		
		this.preencherQtdVolumes = function(qtd) {
		    element(by.id('qtdVolumes')).sendKeys(qtd);
		};
		
		this.preencherQtdApensos = function(qtd) {
		    element(by.id('qtdApensos')).sendKeys(qtd);
		};
		
		this.preencherNumeroSedex = function(qtd){
			element(by.id('numSedex')).sendKeys(qtd);
		};
		
		this.selecionarTipoProcesso = function (tipo) {
			utils.select('div#s2id_tipoProcesso', tipo);
		};
		
		this.registrar = function () {
			element(by.id('btn_exec_registrar-peticao-fisica')).click();
		};
		
		this.registrarVariasPeticoes = function(qtd){
			
			var principalPage;
			
			var i;
			
			for (i = 0 ; i < qtd ; i++){
				
				// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
				principalPage = new PrincipalPage();
				
				// Iniciando o Processo de Remessa Física
				principalPage.iniciarProcesso('link_registrar-peticao-fisica');
				
				this.preencherQtdVolumes(i);
				
				this.preencherQtdApensos(i);
				
				this.classificarTipoRecebimento('Sedex');
				
				this.preencherNumeroSedex(i);
				
				this.selecionarTipoProcesso('Recursal');
				
				this.registrar(); 
			}
			
		};
	};

	module.exports = RegistroPage;
	
})();
