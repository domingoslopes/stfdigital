/**
 * @author Anderson.Araujo
 * 
 * @since 29.02.2016
 */
(function() {
	'use strict';
	
	angular.autuacao.controller('InserePecasController', function ($scope, messages, properties, $window, $cookies, FileUploader) {
		$scope.recursos = [];
		$scope.pecas = [];
		$scope.visibilidades = ["Público", "Pendente de visualização"];
				
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
        
        // FILTERS

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
	    
	    $scope.validar = function() {
	    	var errors = null;
	    	
			if ($scope.partesPoloAtivo.length === 0) {
				errors = 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>ativo</b>.<br/>';
			}
			
			if ($scope.partesPoloPassivo.length === 0) {
				errors += 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>passivo</b>.<br/>';
			}
			
	    	if($scope.pecas.length === 0){
	    		errors += 'Você precisa adicionar <b>pelo menos uma peça</b> na sua petição.<br/>';
	    	}
			
			var tiposSelecionados = [];
			
	    	angular.forEach($scope.pecas, function(peca){
	    		if(peca.tipo) tiposSelecionados.push(peca.tipo);
	    	});
	    	
	    	if(tiposSelecionados.length < $scope.pecas.length){
	    		errors += 'Por favor, classifique todas as peças da sua petição.<br/>';
	    	}
	    	
	    	if(angular.isFunction($scope.child.validar)) {
	    		return $scope.child.validar(errors);
	    	}
	    	return false;
	    };

    	$scope.command = function PeticionarCommand(){
    		var dto = {};
    		    		
    		dto.pecas = pecas;
    		
    		return dto;
    	};
		
	});

})();