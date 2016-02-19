/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 17.08.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var PreautuacaoPage = function () {
		var preautuacao = this;
		
		preautuacao.classificar = function(sigla){
			utils.select('div#s2id_classe', sigla);
		};
		
		preautuacao.selecionarPreferencias = function(preferencia){
			utils.selectMultiple('div#s2id_preferencia', preferencia);
		};
		
		preautuacao.finalizar = function() {
			element(by.css('action-executor[fn-validate="preautuacao.validar"] > a')).click();
		};
		
		preautuacao.finalizarDevolucao = function() {
			element(by.css('action-executor[description="Devolver Petição"] > a')).click();
		};
		
		preautuacao.preencherMotivoIndevida = function(){
			element(by.model('preautuacao.motivo')).sendKeys('Peticao indevida');
		};
		
		preautuacao.preencherAnalise = function(){
			element(by.model('preautuacao.motivo')).sendKeys('Analise realizada');
		};
	};

	module.exports = PreautuacaoPage;
	
})();
