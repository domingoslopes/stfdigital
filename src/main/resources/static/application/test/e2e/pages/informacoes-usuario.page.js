
(function() {
	'use strict';
	
	var InformacoesUsuarioPage = function() {
		
		this.exibirInformacoesUsuario = function() {
			
			//browser.pause();
			//Recupera o menu.
			var btnMenu = element(by.id('papeisButton'));
			btnMenu.click();
			
			//Recupera o link que chamará a página de informações do usuário.
			var lnkInfo = element(by.id('informacoes'));
			lnkInfo.click();
		};
	};
	
	module.exports = InformacoesUsuarioPage;
	
})();