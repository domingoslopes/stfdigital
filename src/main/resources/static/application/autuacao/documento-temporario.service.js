/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.10.2015
 */
(function() {
	'use strict';

	angular.autuacao.service('DocumentoTemporarioService', function ($http, $q, properties) {
		
		this.excluirArquivosTemporarios = function(arquivosTemporarios) {
    		var url = properties.apiUrl + '/documentos/temporarios/delete';
    		var command = new DeleteTemporarioCommand(arquivosTemporarios);
			return $http.post(url, command);
    	};
    	
    	this.limpar = function(arr) {
			arr.splice(0, arr.length);
		};
		
		var DeleteTemporarioCommand = function(files) {
			return {
				files : files
			}; 
		};
		
		this.montarUrlConteudo = function(peca) {
			return properties.apiUrl + '/documentos/' + peca.documentoId + "/conteudo";
		};
		
	});
})();