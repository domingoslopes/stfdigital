/**
 * Service do OnlyOffice.
 * 
 * @author Tomas.Godoi
 */
(function() {
	'use strict';
	
	angular.plataforma.factory('OnlyofficeService', function($http, $q, properties) {
		var baseUrl;
		var getBaseUrl = function() {
			if (!baseUrl) {
				return $http({method: "GET", url: properties.apiUrl + '/onlyoffice/server/baseUrl', transformResponse: function (data, headersGetter, status) {
			        return {url: data};
			    }}).then(function(response) {
					baseUrl = response.data.url;
					return baseUrl;
				});
			} else {
				return baseUrl;
			}
		};
		return {
			criarUrlConteudoDocumento: function(id) {
				return $q.when(getBaseUrl()).then(function(baseUrl) {
					return baseUrl + '/api/onlyoffice/documentos/' + id + '/conteudo.docx';
				});
			},
			recuperarNumeroEdicao: function(id) {
				return $http.get(properties.apiUrl + '/onlyoffice/documentos/' + id + '/edicao').then(function(response) {
					return response.data;
				});
			},
			recuperarUrlCallback: function(id) {
				return $q.when(getBaseUrl()).then(function(baseUrl) {
					return 'https://eti078143:8443/api/onlyoffice/documentos/' + id + '/callback';
				});
			}
		};
	});
})();