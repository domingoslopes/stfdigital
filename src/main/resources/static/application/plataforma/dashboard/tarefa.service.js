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
			listar : function() {
				return $http.get(properties.apiUrl + '/tarefas');
			}
		};
	});
	
})();
