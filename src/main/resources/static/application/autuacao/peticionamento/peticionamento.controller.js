/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('PeticionamentoController', function (data, $scope, $state, messages, properties, $log, $window, $cookies,PeticaoService, FileUploader, PecaService) {
		$scope.child = {};
		$scope.classes = data.data;
		$scope.classe = '';
		$scope.partesPoloAtivo = [];
		$scope.partesPoloPassivo = [];
		$scope.tiposPeca = [];
		$scope.poloAtivoController = new PartesController($scope.partesPoloAtivo);
		$scope.poloPassivoController = new PartesController($scope.partesPoloPassivo);
		$scope.pecas = [];
		$scope.idPeticao;
		
		
		var uploader = $scope.uploader = new FileUploader({
            url: properties.apiUrl + '/documentos/upload/assinado',
            headers : {'X-XSRF-TOKEN': $cookies.get('XSRF-TOKEN')},
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
		
		//remove as peças da petição
		$scope.remover = function(peca, apagarDoServidor) {
			if (apagarDoServidor) {
				var pecaFull = recuperarPecaPorItem(peca.fileItem);
				var arquivoTemporario = [pecaFull.documentoTemporario];
				PecaService.excluirArquivosTemporarios(arquivoTemporario);
			}
			
			peca.fileItem.remove();
			removeArrayItem($scope.pecas, peca);
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
		
		function removeArrayItem(array, item) {
			var index = array.indexOf(item);
			if (index > -1) {
				array.splice(index, 1);
			}
		}
		
		
		$scope.limparPecas = function() {
			var arquivosTemporarios = [];
			angular.forEach($scope.pecas, function(peca) {
				arquivosTemporarios.push(peca.documentoTemporario);
			});
			PecaService.excluirArquivosTemporarios(arquivosTemporarios);
			uploader.clearQueue();
			uploader.progress = 0;
			PecaService.limpar(pecas);
		};
		
		
		$scope.visualizar = function(peca){
		     var file = new Blob([peca.fileItem._file], {type: 'application/pdf'});
             var fileURL = window.URL.createObjectURL(file);
             $window.open(fileURL);
	    };
	    
		$scope.finalizar = function() {
			
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
	    		messages.error('Por favor, classifique todas as peças da sua petição.');
	    		return;
	    	}
	    	
	    	if(angular.isFunction($scope.child.validar)) {
	    		if(!$scope.child.validar()) {
	    			return;
	    		}
	    	}
	    	
	    	if(angular.isFunction($scope.child.finalizar)) {
	    		$scope.child.finalizar();
	    	}
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

    	$scope.command = function PeticionarCommand(classe, partesPoloAtivo, partesPoloPassivo, pecas){
    		var dto = {};
    		dto.classeId = classe;
    		dto.partesPoloAtivo = [];
    		dto.partesPoloPassivo = [];
    		dto.pecas = [];
    		
    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.text);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.text);
    		});
    		
    		angular.forEach(pecas, function(peca){
    			delete peca.fileItem;
    		});
    		
    		dto.pecas = pecas;
    		
    		return dto;
    	};
		
	});

})();

