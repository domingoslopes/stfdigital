/**
 * Verificação da compatibilidade com os requisitos do sistema
 *
 * @author Lucas Mariano
 * 
 * @since 1.0.0
 * @since 29.06.2015
 */
(function () {
	
	'use strict';
   
   /**
    * Classe de teste de compatibilidade de navegador
    */
	function DeviceCompatibility() {
   	
		var errors = '';
		
		/**
		 * Verifica os requisitos
		 * @return errors 
		 */
		this.verifyRequirements = function() {
			//requisitos mínimos de uso
			if (chromeVersion() < 43) {
				errors += '\n\tNavegador Chrome 43+';
			}
			return this;
		};
		
		/**
		 * Mostra erros em um alerta se existirem
		 */
		this.showAlertIfErrors = function() {
			if (errors.length > 0) {
				alert('Seu navegador não possui os seguintes requisitos:' + errors);
			}
		};
		
		/**
		 * Verifica versão do chrome
		 * 
		 * @return número principal da versão ou 0 se não for chrome
		 */
		var chromeVersion = function() {     
		    var raw = navigator.userAgent.match(/Chrom(e|ium)\/([0-9]+)\./);
		    return raw ? parseInt(raw[2], 10) : 0;
		};
   }

   (new DeviceCompatibility()).verifyRequirements().showAlertIfErrors();
   
})();
