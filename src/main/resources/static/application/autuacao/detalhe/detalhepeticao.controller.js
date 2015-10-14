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
		
		detalhe.visualizar = function(pecaId){
		    //var file = new Blob([peca.fileItem._file], {type: 'application/pdf'});
            //var fileURL = window.URL.createObjectURL(file);
            $window.open(api/docummentos/pecaId);
	    };
		
	});
	
})();