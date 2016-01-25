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
		
		preautuacao.finalizar = function() {
			element(by.css('label[for=no]')).click();
		};
		
		preautuacao.finalizarDevolucao = function() {
			element(by.css('action-executor[description="Devolver Petição"] > button')).click();
		};
		
		preautuacao.preencherMotivoIndevida = function(){
			element(by.model('preautuacao.motivo')).sendKeys('Peticao indevida');
		};
	};

	module.exports = PreautuacaoPage;
	
})();
