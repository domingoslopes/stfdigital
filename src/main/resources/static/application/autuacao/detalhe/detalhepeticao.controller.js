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
		
		//detalhe.peticao = {id: idPeticao, classe : 'AP', poloAtivo : ['João', 'Lucas'], poloPassivo : ['GeanCarlo', 'Esdras'], qtdVolumes: '2', qtdApensos : '2', tipoRecebimento: 'Sedex', numSedex: '20'};
		
		
	});
	
})();