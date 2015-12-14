/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 10.12.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('CPFService', function(properties, $http) {
		/**
		 * Valida um CPF
		 * 
		 * @param string numero O número do cpf, sem ponto ou traço
		 * @return boolean Validade do CPF 
		 */
		this.validarCPF = function validarCPF(numero) {
			var soma = 0; 
			var resto; 

			// Verifica tamanho
			if (numero.length != 11) {
				return false;
			}
			
			// Verifica CPF Padrão
			if (this._isCPFPadrao(numero)) {
				return false;
			}
			
			// Calcula primeiro dígito verificador
			for (var i = 1; i <= 9; i++) { 
				soma = soma + parseInt(numero.substring(i-1, i)) * (11 - i); 
			}
			resto = (soma * 10) % 11; 
			
			if ((resto == 10) || (resto == 11)) { 
				resto = 0; 
			}
			
			// Verifica primeiro dígito
			if (resto != parseInt(numero.substring(9, 10))) {
				return false; 
			}
			
			// Calcula segundo dígito verificador
			soma = 0; 
			for (i = 1; i <= 10; i++) {
				soma = soma + parseInt(numero.substring(i-1, i)) * (12 - i);
			}
			resto = (soma * 10) % 11; 
			
			if ((resto == 10) || (resto == 11)) { 
				resto = 0; 
			}
			
			// Verifica segundo dígito
			if (resto != parseInt(numero.substring(10, 11))) { 
				return false; 
			}
			
			return true;
		}
		
		// PRIVATE
		
		/**
		 * Verifica se o CPF é padrão
		 * 
	 	 * @param string numero O número do cpf, sem ponto ou traço
		 * @return boolean  
		 */
		this._isCPFPadrao = function(numero) {
			return (
				(numero == "00000000000") ||
				(numero == "11111111111") ||
				(numero == "22222222222") ||
				(numero == "33333333333") ||
				(numero == "44444444444") ||
				(numero == "55555555555") ||
				(numero == "66666666666") ||
				(numero == "77777777777") ||
				(numero == "88888888888") ||
				(numero == "99999999999")
			);
		}
	});
})();
