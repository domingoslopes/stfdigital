/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 11.12.2015
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var CadastroAssociado = function () {
		
		this.classificarClasse = function(sigla) {
			utils.select('div#s2id_classe', sigla);
		};
		
		this.orgao = function(nome) {
			utils.select('div#s2id_orgao', nome);
		};
		
		this.nome = function(nome) {
		    element(by.id('nome')).sendKeys(nome);
		};
		
		this.cpf = function(nome) {
		    element(by.id('cpf')).sendKeys(nome);
		};
		
		this.cargo = function(nome) {
		    element(by.id('cargo')).sendKeys(nome);
		};
		
		this.tipoAssociacao = function(nome) {
			var id;
			
			switch (nome) {
			case 'Gestor':
				id = 'tipoAssociacao0';
				break;
			case 'Representante':
				id = 'tipoAssociacao1';
				break;
			case 'Associado':
				id = 'tipoAssociacao2';
				break;
			default:
				return;
			}
			
		    element(by.css("label[for=" + id + "]")).click();
		};
		
		this.enviar = function() {
			element(by.css('button[type=submit]')).click();
		}
	};
	
	module.exports = CadastroAssociado;
})();