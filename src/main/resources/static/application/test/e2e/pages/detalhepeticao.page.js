/**
 * @author Tomas.Godoi
 */
/*jshint undef:false */
(function() {
	'use strict';

	var Utils = require('./support');
	
	var utils = new Utils();
	
	var fs = require('fs');
	
	var filename = '/tmp/download.pdf';
	
	var DetalhePeticaoPage = function () {
		
		this.linhasPecas = function() {
			return element.all(by.repeater('peca in peticao.pecas'));
		};
		
		this.visualizarPeca = function(index) {
			var linhaPeca = element.all(by.repeater('peca in peticao.pecas')).get(index);
			
			var linkVisualizar = linhaPeca.element(by.css('a[ng-href]'));
			
			// Adiciona o atributo download para que o Chrome faça o download ao invés de abrir em nova aba.
			browser.driver.executeScript('arguments[0].setAttribute("download", "download");', linkVisualizar.getWebElement());
			
			if (fs.existsSync(filename)) {
			    // Se existir, remove para o browser não ter que renomear o download.
			    fs.unlinkSync(filename);
			}
			
			linkVisualizar.click();
			
			return browser.wait(function() {
				return fs.existsSync(filename);
			}, 120000);
		};
		
	};

	module.exports = DetalhePeticaoPage;
	
})();
