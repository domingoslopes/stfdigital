/**
*
*
**/

(function(){
	'use strict';

	angular.plataforma.controller('DetalhePeticaoController', function ($log, $state, $stateParams, messages, properties, PeticaoService) {
		
		var detalhe = this;
		
		detalhe.peticao = {};
		
		var idPeticao = $stateParams.idPeticao;
		
		PeticaoService.consultar(idPeticao).success(function(data) {
			detalhe.peticao = data;
		});
		
		detalhe.urlConteudo = function(peca) {
			return properties.apiUrl + '/documentos/' + peca.documentoId;
		};
		
		
	});
	
})();