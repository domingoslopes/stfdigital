/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('OrganizaPecasController', function ($stateParams, $state, $cookies, messages, properties,ProcessoService, FileUploader, PeticaoService, PecaService, DTOptionsBuilder) {
		
		var organiza = this;
		
		organiza.child = {};
		
		var resource = $stateParams.resources[0];
		
		organiza.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		
		organiza.tiposPeca = [];
		
		organiza.resultados = [];
		
		organiza.recursos = [];
		
		organiza.habilitado = true;
		
//		organiza.pecas = [];
		
		organiza.buildSelectedObject = function(item) {
			return {'documentoId': item.documentoId};
		};
		
		organiza.atualizaEstado = function(){
			if (organiza.habilitado){
				organiza.habilitado = false;
			}else{
				organiza.habilitado = true;
			}
		};
		
		ProcessoService.consultarPorPeticao(organiza.peticaoId).success(function(data){
			organiza.processo = data;
			organiza.resultados = data.pecas;
		});
		
//		ProcessoService.consultarPecas(organiza.processo.id).success(function(data){
//			organiza.pecas = data;
//		});
		
		organiza.toggleCheck = function() {
			if (organiza.checkToggle) {
				organiza.selecao.objetos = organiza.resultados.map(function(item) {
					return organiza.buildSelectedObject(item);
				});
			} else {
				organiza.selecao.objetos = [];
			}
		};
		
		organiza.selecao = {
			objetos: []
		};
		
		organiza.dtOptions = DTOptionsBuilder.newOptions()
	        .withDOM('Bptr');
		
		organiza.sortableOptions = {
			helper : fixWidthHelper,
			disabled: (organiza.habilitado ? false : true)
		};
		
		function fixWidthHelper(e, ui) {
		    ui.children().each(function() {
		        $(this).width($(this).width());
		    });
		    return ui;
		}
		
	/*	var uploader = organiza.uploader = new FileUploader({
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
            organiza.pecas.push(peca);						
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
        	organiza.remover(peca, false);
        };
        
        // FILTERS

        uploader.filters.push({
            name: 'customFilter',
            fn: function(item {File|FileLikeObject}, options) {
                return this.queue.length < 10;
            }
        });
		

		organiza.configurarSelect2 = function(idx) {
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
		
		
		//remove as peças da petição
		organiza.remover = function(peca, apagarDoServidor) {
			if (apagarDoServidor) {
				var pecaFull = recuperarPecaPorItem(peca.fileItem);
				var arquivoTemporario = [pecaFull.documentoTemporario];
				PecaService.excluirArquivosTemporarios(arquivoTemporario);
			}
			
			peca.fileItem.remove();
			removeArrayItem(organiza.pecas, peca);
		};
		
		function recuperarPecaPorItem(item) {
			return item.peca;
		}
		
		function removeArrayItem(array, item) {
			var index = array.indexOf(item);
			if (index > -1) {
				array.splice(index, 1);
			}
		}
		
		
		organiza.limparPecas = function() {
			var arquivosTemporarios = [];
			angular.forEach(organiza.pecas, function(peca) {
				arquivosTemporarios.push(peca.documentoTemporario);
			});
			PecaService.excluirArquivosTemporarios(arquivosTemporarios);
			uploader.clearQueue();
			uploader.progress = 0;
			PecaService.limpar(pecas);
		};
		
		
		organiza.visualizar = function(peca){
		     var file = new Blob([peca.fileItem._file], {type: 'application/pdf'});
             var fileURL = window.URL.createObjectURL(file);
             $window.open(fileURL);
	    };*/
	    
	    organiza.validar = function() {
	    	var errors = null;
	    	
	    	if (errors) {
				messages.error(errors);
				return false;
			}
			organiza.recursos.push( new ControlePecasCommand(organiza.processo.id, organiza.resultados, true));
			return true;
	    };
	    
	    organiza.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + organiza.processo.identificacao + '</b> organizado com sucesso.');
		};
		
		function ControlePecasCommand(id, pecas, concluirTarefa){
    		var dto = {};
    		dto.processoId = id;
    		dto.pecasOrganizadas = [];
    		dto.concluirTarefa = concluirTarefa;
    		
    		angular.forEach(pecas, function(peca) {
    			dto.pecasOrganizadas.push(peca.id);
    		});
    		
    		return dto;
    	};
		
	});

})();

