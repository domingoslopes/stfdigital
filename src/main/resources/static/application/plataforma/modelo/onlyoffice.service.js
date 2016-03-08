/**
 * Service do OnlyOffice.
 * 
 * @author Tomas.Godoi
 */
(function() {
	'use strict';
	
	angular.plataforma.factory('OnlyofficeService', function($http, properties) {
		return {
			criarUrlConteudoDocumento: function(id) {
				return 'https://eti078143:8443/api/onlyoffice/documentos/' + id + '/conteudo.docx';
			},
			recuperarNumeroEdicao: function(id) {
				return $http.get(properties.apiUrl + '/onlyoffice/documentos/' + id + '/edicao').then(function(response) {
					return response.data;
				});
			},
			recuperarUrlCallback: function(id) {
				return 'https://eti078143:8443/api/onlyoffice/documentos/' + id + '/callback';
			}
		};
	});
})();