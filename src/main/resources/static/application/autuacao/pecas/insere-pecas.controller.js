/**
 * @author Anderson.Araujo
 * 
 * @since 29.02.2016
 */
(function() {
	'use strict';
	
	angular.autuacao.controller('InserePecasController', function ($scope, $stateParams, $state, messages, properties, $window, $cookies, FileUploader, PeticaoService, DocumentoTemporarioService, ProcessoService) {
		$scope.peticaoId = angular.isObject($stateParams.resources[0]) ? $stateParams.resources[0].peticaoId : $stateParams.resources[0];
		$scope.processo = null;
		$scope.pecas = [];
		$scope.visibilidades = [{id:"PUBLICO", descricao:"Público"}, {id:"PENDENTE_VISUALIZACAO", descricao:"Pendente de visualização"}];
		$scope.tiposPecas = [];
		$scope.recursos = [];

		PeticaoService.listarTipoPecas().then(function(tiposPecas) {
			$scope.tiposPecas = tiposPecas;
		});
		
		ProcessoService.consultarPorPeticao($scope.peticaoId).success(function(data){
			$scope.processo = data;
		});
		
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
                	tipo : null,
                	descricao : null,
                	visibilidade : null,
                	uploadFinished: false
            };
            fileItem.peca = peca;
            $scope.pecas.push(peca);
			fileItem.upload();
		};
		
        uploader.onSuccessItem = function(fileItem, response, status) {
        	var peca = recuperarPecaPorItem(fileItem);
       		peca.documentoTemporario = response;
       		peca.uploadFinished = true;
        };
        
        uploader.onErrorItem = function(fileItem, response, status) {
        	var peca = recuperarPecaPorItem(fileItem);
        	if (status === 0) {
        		// O status 0 provavelmente foi porque a conexão foi resetada por ultrapassar
        		// o tamanho máximo de 10 MB no backend.
        		messages.error('Não foi possível anexar o arquivo "' + fileItem.file.name + '". <br />O tamanho do arquivo excede 10mb.');
        	} else {
        		messages.error(messages.buildErrorMessage(response));
        	}
        	$scope.remover(peca, false);
        };

        uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });
		
		function recuperarPecaPorItem(item) {
			return item.peca;
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
			
			DocumentoTemporarioService.excluirArquivosTemporarios(arquivosTemporarios);
			uploader.clearQueue();
			uploader.progress = 0;
			DocumentoTemporarioService.limpar($scope.pecas);
		};
		
		$scope.visualizar = function(peca){
		     var file = new Blob([peca.fileItem._file], {type: 'application/pdf'});
             var fileURL = window.URL.createObjectURL(file);
             $window.open(fileURL);
	    };
	    
	    //remove uma peça.
		$scope.removerPeca = function(peca, apagarDoServidor) {
			if (apagarDoServidor) {
				var pecaFull = recuperarPecaPorItem(peca.fileItem);
				var arquivoTemporario = [pecaFull.documentoTemporario];
				DocumentoTemporarioService.excluirArquivosTemporarios(arquivoTemporario);
			}
			
			peca.fileItem.remove();
			removeArrayItem($scope.pecas, peca);
		};
	    
	    $scope.validar = function() {
	    	var errors = '';
	    	var camposErros = '';
	    	var pecaInserida = null;
			
			if ($scope.pecas.length === 0) {
				errors += 'Nenhuma peça foi informada.<br/>';
			} else {
				for (var i = 0; i < $scope.pecas.length; i++) {
					pecaInserida = $scope.pecas[i];
					
					if (pecaInserida.descricao == '' || pecaInserida.descricao == null) {
						camposErros += '- Descrição<br/>';
					}
					
					if (pecaInserida.visibilidade == null) {
						camposErros += '- Visibilidade<br/>';
					}
					
					if (pecaInserida.tipo == null) {
						camposErros += '- Tipo de peça<br/>';
					}
					
					if (camposErros != '') {
						errors += 'Campo(s) não informado(s) para a peça ' + pecaInserida.fileItem.file.name + ': <br/>' + camposErros + '<br/>';
					}
					
					if (errors) {
						messages.error(errors);
					}
				}
			}
			
	    	if (errors) {
				return false;
			}
	    	
	    	$scope.recursos[0] = new SalvarPecasCommand($scope.processo.id, $scope.pecas);
	    	
	    	return true;
	    };
	    
	    $scope.completar = function() {
	    	$state.go('organizar-pecas');
	    	messages.success('Peças inseridas com sucesso.');
	    };

    	function SalvarPecasCommand(processoId, pecas){
    		var dto = {};
    		
    		dto.processoId = processoId;
    		dto.pecas = montarDtoPecas(pecas);
    		
    		return dto;
    	};
		
    	function montarDtoPecas(pecas) {
    		var pecasDto = [];
    		
    		for (var i = 0; i < pecas.length; i++) {
    			var dto = {};
    			dto.documentoTemporarioId = pecas[i].documentoTemporario;
        		dto.tipoPecaId = pecas[i].tipo;
        		dto.visibilidade = pecas[i].visibilidade;
        		dto.situacao = 'Pendente de juntada';
        		dto.descricao = pecas[i].descricao;
        		
        		pecasDto.push(dto);
    		}
    		
    		return pecasDto;
    	};
	});

})();