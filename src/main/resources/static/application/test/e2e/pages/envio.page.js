/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 04.04.2016
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var Utils = require('./support');
	
	var utils = new Utils();
	
	var EnvioPage = function () {
		
		this.clicaBotaoEnvioProcesso = function(){
            element(by.id('btn_enviar-processo')).click();   
        };
        
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
        
        this.salvarProcesso = function(){
            element(by.id('btnSalvar')).click();
        };
        
        this.selecionarPrimeiroProcesso = function(){
            element.all(by.repeater('processo in processos')).
			  get(0).element(by.css('input')).click();
        };
        
        this.selecionarTarefaEnviaProcesso = function(){
            selecionaAcao(0);
            browser.waitForAngular();
        };
		
		this.classificarTribunalJuizo = function(juizo){
			utils.select('div#s2id_origem', juizo);
            browser.waitForAngular();
		};
		
		this.preencherNumeroOrigem = function(){
			var inputOrigem = element(by.id("numeroOrigem"));
			inputOrigem.sendKeys(1000, protractor.Key.ENTER);
            browser.waitForAngular();
		};
        
        this.setaOrigemPrincipal = function(){
            browser.wait(browser.isElementPresent(by.id('ckOrigemPrincipal')));
            element.all(by.repeater('origem in envio.origens')).get(0).
                element(by.css('input[ng-click="envio.marcarOrigemPrincipal($index)"]')).click();
        }
		
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
        
        var selecionaAcao = function(indiceAcao){
			var acao = element(by.css('actions'));
			acao.element(by.css('a.dropdown-toggle')).click();
			acao.element(by.repeater('action in actions').row(indiceAcao)).click();
			browser.waitForAngular();
		};
		
	};

	module.exports = EnvioPage;
	
})();
