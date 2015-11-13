/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0.M4
 * @since 12.11.2015
 */
(function() {
	'use strict';

	angular.plataforma.controller('GestaoAutuacaoDashletController', function($scope, $log, PesquisaService) {
		$scope.titulo = 'Petições Autuadas';
		
		$scope.nvd3 = {};

		$scope.options = {
			chart : {
				type : 'pieChart',
				noData: "",
				height : 300,
				margin : {top : 20, right : 20, bottom : 20, left : 55},
				x : function(d) {
					return d.label;
				},
				y : function(d) {
					return d.value;
				},
				showValues : true
			}
		};
		
		$scope.data = [];
		
		var command = new PesquisarCommand();
		
		PesquisaService.pesquisar(command).then(function(eventos) {
			angular.forEach(eventos.data, function(evento) {
				angular.forEach(Object.keys(evento.objeto), function(key) {
					$scope.data.push({"label": key, "value" : evento.objeto[key]});
				});
			});
			$scope.nvd3.api.refresh();
		});
		
    	function PesquisarCommand() {
    		var dto = {};
    		
    		dto.indices = ['quantidade-autuacoes'];
    		
    		dto.filtros = {};
			dto.filtros.mesAutuacao = [new Date().getMonth() + 1];
			
			dto.campos = ["classeProcessual"];
			
			dto.campoAgregacao = "classeProcessual";
			
    		return dto;
    	};
		
	});

})();
