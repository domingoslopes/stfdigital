/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PeticionamentoController', function (data, $scope, $state, messages, properties, $log, PeticaoService) {
		$scope.classes = data.data;
		
		$scope.classe = '';
		
		$scope.partesPoloAtivo = [];
		
		$scope.partesPoloPassivo = [];
		
		$scope.poloAtivoController = new PartesController($scope.partesPoloAtivo);
		
		$scope.poloPassivoController = new PartesController($scope.partesPoloPassivo);
		
		$scope.adicionarPoloAtivo = function() {
			$scope.poloAtivoController.adicionar($scope.partePoloAtivo);
			$scope.partePoloAtivo = '';
			$('partePoloAtivo').focus();
		};
	
		$scope.removerPoloAtivo = function(parteSelecionada) {
			$scope.poloAtivoController.remover(parteSelecionada);
		};

		$scope.adicionarPoloPassivo = function() {
			$scope.poloPassivoController.adicionar($scope.partePoloPassivo);
			$scope.partePoloPassivo = '';
			$('partePoloPassivo').focus();
		};
	
		$scope.removerPoloPassivo = function(parteSelecionada) {
			$scope.poloPassivoController.remover(parteSelecionada);
		};

		$scope.finalizar = function() {
			if ($scope.classe.length === 0) {
				messages.error('Você precisa selecionar <b>a classe processual sugerida</b>.');
				return;
			}
			
			if ($scope.partesPoloAtivo.length === 0) {
				messages.error('Você precisa informar <b>pelo menos uma parte</b> para o polo <b>ativo</b>.');
				return;
			}
			
			if ($scope.partesPoloPassivo.length === 0) {
				messages.error('Você precisa informar <b>pelo menos uma parte</b> para o polo <b>passivo</b>.');
				return;
			}
			
			PeticaoService.peticionar(new PeticionarCommand($scope.classe, $scope.partesPoloAtivo, $scope.partesPoloPassivo)).success(function(data) {
				$state.go('dashboard');
				//messages.success('Petição <b>#' + data + '</b> enviada com sucesso.');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pôde ser registrada</b> porque ela não está válida.');
				}
			});
			
		};
		
    	function PartesController(partes){
    		var partesController = {};
    		
    		partesController.adicionar = function(parte) {
				partes.push({
					text : parte,
					selected : false
				});
			};
		
			partesController.remover = function(parteSelecionada) {
				parteSelecionada.selected = true;
				var partesAtuais = partes.slice(0);
				partesController.clear(partes);
				angular.forEach(partesAtuais, function(parte) {
					if (!parte.selected) {
						partes.push(parte);
					}
				});
			};
			
			partesController.clear = function(array) {
				while (array.length) {
					array.pop();
				}
			};
			
			return partesController;
		}


    	function PeticionarCommand(classe, partesPoloAtivo, partesPoloPassivo){
    		var dto = {};
    		
    		dto.classe = classe;
    		
    		dto.partesPoloAtivo = [];
    		
    		dto.partesPoloPassivo = [];
    		
    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.text);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.text);
    		});
    		
    		return dto;
    	}
		
	});

})();

