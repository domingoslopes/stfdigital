/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PeticionamentoController', function (data, $scope, $state, messages, properties, $log, PeticaoService, FileUploader) {
		$scope.classes = data.data;
		$scope.classe = '';
		$scope.partesPoloAtivo = [];
		$scope.partesPoloPassivo = [];
		$scope.tiposPeca = [];
		$scope.poloAtivoController = new PartesController($scope.partesPoloAtivo);
		$scope.poloPassivoController = new PartesController($scope.partesPoloPassivo);
		$scope.pecas = [];
		
		
		var uploader = $scope.uploader = new FileUploader({
            url: properties.apiUrl + '/documentos/upload',
            formData: [{name: "file"}],
            filters: [{
		    	name: 'arquivos-pdf',
		    	fn: function (file) {
		    		if (file.type === 'application/pdf') {
			    		return true;
		    		} else {
		    			messages.error('Não foi possível anexar o arquivo "' + file.name + '". <br />Apenas documentos *.pdf são aceitos.');
		    			return false;
		    		}
		    	}
		    }, {
		    	name: 'tamanho-maximo',
		    	fn: function(file) {
		    		if (file.size / 1024 / 1024 > 10) {
		    			messages.error('Não foi possível anexar o arquivo "' + file.name + '". <br />O tamanho do arquivo excede 10mb.');
		    			return false;
		    		}
		    		return true;
		    	}
		    }]
        });
		
		uploader.onAfterAddingFile = function(fileItem) {
            var peca = {
            		fileItem : fileItem,
                	documentoTemporario : null,
                	tipo : null
            };
            $scope.pecas.push(peca);						
			fileItem.upload();
		};
		
        uploader.onCompleteItem = function(fileItem, response) {
        	var peca = recuperarPecaPorItem(fileItem);
        	peca.documentoTemporario = response;
        };
        
		function recuperarPecaPorItem(item) {
			var p = null;
			angular.forEach($scope.pecas, function(peca) {
				if (peca.fileItem == item) {
					p = peca;
				}
			});
			return p;
		}
		
        // FILTERS

        uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });
		

		$scope.configurarSelect2 = function(idx) {
			return {
				dropdownCssClass: 'select2-resultado-tipo-peca-' + idx,
				containerCss: {
					'min-width': '100%'
				},
				formatSelection: function (item) {
					var originalText = item.text;
			        return "<div data-original-title='" + originalText + "'>" + originalText + "</div>";
				},
				formatResult: function(item) {
					return item.text;
				}
			};
		};
		
		PeticaoService.listarTipoPecas().then(function(tiposPeca) {
			$scope.tiposPeca = tiposPeca;
		});
		
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
		
		/*$scope.getPecas = function() {
			return PeticaoService.getPeticao().pecas;
		};*/
		
		//$scope.pecaVisualizadaFile = undefined;
		$scope.visualizar = function(item){
			
			window.open('api/documento/' + item,'._file','');
			//$scope.pecaVisualizadaFile = item._file;
	    	//$scope.$emit("event:showModal", "divModalVizualizarPeca");
	    };
	    
	   /* $scope.fecharModal = function() {
	    	$scope.pecaVisualizadaFile = undefined;
	    };*/

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
			
			var tiposSelecionados = [];
			
	    	angular.forEach($scope.pecas, function(peca){
	    		if(peca.tipo) tiposSelecionados.push(peca.tipo);
	    	});
	    	
	    	if(tiposSelecionados.length < $scope.pecas.length){
	    		messages.error('Por favor, classifique todas as pe�as da sua peti��o.');
	    		
	    		return;
	    	}
	    	
			var command = new PeticionarCommand($scope.classe, $scope.partesPoloAtivo, $scope.partesPoloPassivo, $scope.pecas);
			
			PeticaoService.peticionar(command).success(function(data) {
				$state.go('dashboard');
				messages.success('Petição <b>#' + data + '</b> enviada com sucesso.');
			}).error(function(data, status) {
				if (status === 400) {
					messages.error('A Petição <b>não pôde ser registrada</b> porque ela não está válida.');
				}
			});
			
		};
		
		
	//-----------------------------------------------------------------------------------------
		
        //uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
        /*    console.info('onWhenAddingFileFailed', item, filter, options);
        };

        uploader.onAfterAddingAll = function(addedFileItems) {
            console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function(item) {
            console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function(fileItem, progress) {
            console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function(progress) {
            console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function(fileItem, response, status, headers) {
            console.info('onCancelItem', fileItem, response, status, headers);
        };
        /*uploader.onCompleteItem = function(fileItem, response, status, headers) {
            console.info('onCompleteItem', fileItem, response, status, headers);
        };*/
        /*uploader.onCompleteAll = function() {
            console.info('onCompleteAll');
        };

        console.info('uploader', uploader);*/
		
		//-----------------------------------------------------------------------------------------
		
		
		
		
		
		
		
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


    	function PeticionarCommand(classe, partesPoloAtivo, partesPoloPassivo, pecas){
    		var dto = {};
    		dto.classeId = classe;
    		dto.partesPoloAtivo = [];
    		dto.partesPoloPassivo = [];
    		dto.documentos = [];
    		
    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.text);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.text);
    		});
    		
    		angular.forEach(pecas, function(peca){
    			delete peca.fileItem;
    		})
    		
    		dto.documentos = pecas;
    		
    		return dto;
    	}
		
	});

})();

