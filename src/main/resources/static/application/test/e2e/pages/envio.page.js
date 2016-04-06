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
	
	var utils = new Utils();
	
	var EnvioPage = function () {
		
		
		this.classificarClasse = function(sigla) {
			utils.select('div#s2id_classe', sigla);
		};
		
		this.classificarSigilo = function(valor) {
			utils.select('div#s2id_sigilo', valor);
		};
		
		this.preencherRecurso = function(valor){
			element(by.id('recuso')).sendKeys(valor);
		};
		
		this.selecionarPreferencias = function(preferencia){
			utils.selectMultiple('div#s2id_preferencia', preferencia);
		};
		
		this.classificarProcedencia = function(procedencia){
			utils.select('div#s2id_procedencia', procedencia);
		};
		
		this.classificarTribunalJuizo = function(juizo){
			utils.select('div#s2id_origem', juizo);
		};
		
		this.preencherNumeroOrigem = function(){
			var inputOrigem = element(by.id("numeroOrigem"));
			inputOrigem.sendKeys(1000, protractor.Key.ENTER);
		};
		
		this.classificarAssuntos = function(assunto){
			var inputAssunto = element(by.id('txtPesquisarAssunto'));
			inputAssunto.sendKeys(assunto, protractor.Key.ENTER);
			element.all(by.repeater('assunto in assunto.assuntosFilhos')).
			  get(0).element(by.css('a')).click();
		};
        
        this.adicionarPoloAtivo = function(nome){
            var inputPoloAtivo = element(by.id("txtPesquisaParte"));
            inputPoloAtivo.sendKeys(nome, protractor.Key.ENTER);
        };
        
        this.setaPoloPassivo = function(){
            element(by.id('btnPassivo')).click();
        };
        
        this.adicionarPoloPassivo = function(nome){
            var inputPoloPassivo = element(by.id("txtPesquisaParte"));
            inputPoloPassivo.sendKeys(nome, protractor.Key.ENTER);
        };
		
		this.preencherObsAnalise = function(){
			element(by.id('obsAnalise')).sendKeys('observação analise');
		};
		
        this.finalizarEnvio = function() {
            var btn = by.id('btn_exec_enviar-processo')
            browser.wait(browser.isElementPresent(btn));
            element(btn).click();
        };
		
	};

	module.exports = EnvioPage;
	
})();
