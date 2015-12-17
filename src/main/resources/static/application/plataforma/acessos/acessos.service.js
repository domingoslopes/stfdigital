/**
 * Service de acessos
 * 
 * @author Gabriel Teles
 * @since 16/12/2015
 */
(function() {
	'use strict';
	
	angular.plataforma.service('AcessosService', function(properties, $http) {
		/**
		 * Recupera o ID a partir do login de um usuário
		 * 
		 * @param String login Login do usuário
		 * @return Promise Promessa de ID do usuário
		 */
		this.recuperarId = function(login) {
			return $http.get(properties.apiUrl + '/acessos/usuarios/id', {params: {login: login}});
		}
		
		/**
		 * Retorna todos os grupos do sistema ou grupos de um usuário
		 * 
		 * @param String login (Opcional) Login do usuário
		 * @return Promise Promessa de grupos do usuário
		 */
		this.grupos = function(login) {
			if (login) {
				return $http.get(properties.apiUrl + '/acessos/usuarios/grupos', {params: {login: login}});
			} else {
				return $http.get(properties.apiUrl + '/acessos/grupos');	
			}
		}
		
		/**
		 * Retorna todos os papeis do sistema
		 * 
		 * @param String login (Opcional) Login do usuário
		 * @return Promise Promessa de papeis do usuário
		 */
		this.papeis = function(login) {
			if (login) {
				return $http.get(properties.apiUrl + '/acessos/usuarios/papeis', {params: {login: login}});
			} else {
				return $http.get(properties.apiUrl + '/acessos/papeis');
			}
		}
	
		/**
		 * Altera a configuração de permissões de um usuário
		 */
		this.configurarPermissoes = function(detalhes) {
			var command = new ConfigurarPermissoesUsuarioCommand(detalhes);
			return $http.post(properties.apiUrl + '/acessos/permissoes/configuracao', command);
		}
		
		/**
		 * Command de configuração de permissões de usuário
		 */
		var ConfigurarPermissoesUsuarioCommand = function(detalhes) {
			this.idUsuario = detalhes.idUsuario;
			this.papeisAdicionados = detalhes.papeisAdicionados;
			this.papeisRemovidos = detalhes.papeisRemovidos;
			this.gruposAdicionados = detalhes.gruposAdicionados;
			this.gruposRemovidos = detalhes.gruposRemovidos;
		}
	});
})();