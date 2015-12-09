/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 09.12.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('AssociadoService', function(properties, $http) {
		/**
		 * Cadastra um associado
		 * 
		 * @param Object Detalhes do cadastro
		 * @return Promise
		 */
		this.cadastrar = function cadastrar(detalhes) {
			var command = new AssociadoCommand(detalhes);
			return $http.post(properties.apiUrl + '/associado', command);
		};
		
		/**
		 * Comando para cadastrar um usuário
		 * 
		 * @param Object Detalhes do cadastro
		 */
		var AssociadoCommand = function(detalhes) {
			this.idOrgao = detalhes.idOrgao;
			this.cpf = detalhes.cpf;
			this.nome = detalhes.nome;
			this.tipoAssociacao = detalhes.tipoAssociacao;
			
			if (detalhes.cargo != null && detalhes.cargo.length > 0) {
				this.cargo = detalhes.cargo;	
			}			
		};
	});
	
})();
