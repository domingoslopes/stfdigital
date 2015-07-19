/**
 * Fornece serviços para realizar operações persistentes sobre as tarefas de
 * um usuário, papel ou grupo de usuários dentro de um ou mais 
 * processos de negócio
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 08.07.2015
 */
(function() {
	'use strict';

	angular.module('plataforma').factory('TarefaService', function($http, $window) {
		return {
			listar : function() {
				/**
				var papel = JSON.parse($window.sessionStorage["papel"]).nome
				return $http.get('/api/tarefas', {
				    headers: {'papel': papel}
				});
				*/
				return $http.get('/api/tarefas');
			}
		};
	});
	
})();
