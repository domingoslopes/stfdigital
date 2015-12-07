/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 03.12.2015
 */
(function() {
	'use strict';

	angular.plataforma.service('CadastroService', function(properties, $http) {
		/**
		 * Cadastra um usuário
		 * 
		 * @param Object Detalhes do cadastro
		 * @return Promise
		 */
		this.cadastrar = function cadastrar(detalhes) {
			var command = new CadastrarUsuarioCommand(detalhes);
			return $http.post(properties.apiUrl + '/acessos/usuarios', command);
		};
		
		/**
		 * Comando para cadastrar um usuário
		 * 
		 * @param Object Detalhes do cadastro
		 */
		var CadastrarUsuarioCommand = function(detalhes) {
			this.login = detalhes.login;
			this.nome = detalhes.nome;
			this.email = detalhes.email;
			this.cpf = detalhes.cpf;
			this.oab = detalhes.oab;
			this.telefone = detalhes.telefone;
			//this.senha = detalhes.senha;
		};
	});
	
})();
