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

	angular.plataforma.factory('TarefaService', function($http, $window, properties) {
		return {
			listarMinhas : function() {
				return $http.get(properties.apiUrl + '/workflow/tarefas');
			},
			listarPorMeusPapeis : function() {
				return $http.get(properties.apiUrl + '/workflow/tarefas/papeis');
			},
			consultarPorProcesso : function(processoId) {
				return $http.get(properties.apiUrl + '/workflow/tarefas/processos/' + processoId).then(function(response) {
					return response.data;
				});
			},
			consultarPorProcessos : function(processosId) {
				var params = {ids : processosId };
				return $http.get(properties.apiUrl + '/workflow/tarefas/processos', { params : params }).then(function(response) {
					return response.data;
				});
			}
		};
	});
	
})();
