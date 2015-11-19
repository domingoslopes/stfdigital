/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.10.2015
 */
(function() {
	'use strict';

	angular.autuacao.service('PecaService', function ($http, $log, $q) {
		
		this.excluirArquivosTemporarios = function(arquivosTemporarios) {
    		var url = 'api/documento/files/delete';
    		$log.info("Excluindo arquivos temporários: ");
    		$log.info(arquivosTemporarios);
			return $http.post(url, arquivosTemporarios).success(function(data, status) {
				$log.info("Resposta: (" + status + ")");
			});
    	};
    	
    	this.limpar = function(arr) {
			arr.splice(0, arr.length);
		};
		
	});
})();