/**
 * @author Gabriel Teles
 * 
 * @since 03.12.2016
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('RevisaoRepercussaoGeralController', function ($scope, $state, $stateParams, messages, properties, ProcessoService) {
		
		var revisao = this;
		var resource = $stateParams.resources[0];
		revisao.assuntos = [];
		revisao.tiposTeses = [{id:"CONTROVERSIA", descricao : "Controvérsia"}, 
		                 {id:"PRE_TEMA", descricao : "Pré-tema"},
		                 {id:"REPERCUSSAO_GERAL", descricao : "Repercussão Geral"}];
		revisao.tipoTese = revisao.tiposTeses[0];
		revisao.numeroTese = '';
		revisao.recursos = [];
		
		if (angular.isObject(resource)) {
			if (angular.isDefined(resource.peticaoId)) {
				revisao.id = resource.peticaoId;
			} else if (angular.isDefined(resource.processoId)) {
				revisao.id = resource.processoId;
			}
		} else {
			revisao.id = resource;
		}
		
		var consultarProcesso = null;
		revisao.tarefa = $stateParams.task;
		
		if (revisao.tarefa.metadado.tipoInformacao == 'ProcessoRecursal') {
			consultarProcesso = ProcessoService.consultar(revisao.id);
		} else {
			consultarProcesso = ProcessoService.consultarPorPeticao(revisao.id);
		}
		consultarProcesso.success(function(processo) {
			revisao.processo = processo;
			revisao.observacao = processo.observacaoAnalise;
			revisao.teses = processo.teses;
			revisao.assuntos = processo.assuntos;
		});
		
		revisao.selecionarTipoTese = function(tipo) {
			revisao.tipoTese = tipo;
		};
		
		revisao.consultarTese = function() {
			var soNumeros = /^[0-9]+$/g;
			
			if (!soNumeros.test(revisao.numeroTese)) {
				return false;
			}
			
			ProcessoService.consultarTeses(revisao.tipoTese.id, revisao.numeroTese).then(function(resposta) {
				var teseConsultada = resposta.data[0];
				
				if (teseConsultada !== undefined){
					var assuntosConsultados = teseConsultada.assuntos;  
					
					revisao.adicionarTese(teseConsultada);
					
					for (var i = 0; i < assuntosConsultados.length; i++) {
						revisao.adicionarAssuntoNaLista({codigo : assuntosConsultados[i].codigo, descricao : assuntosConsultados[i].descricao, codigoTese : teseConsultada.codigo});
					}
					
					revisao.numeroTese = null;
				} else {
					messages.error('Tese não encontrada');
				}
			}).catch(function(resposta) {
				messages.error(resposta.data.message);
			})
		};
		
		revisao.adicionarTese = function(tese){
			var teseAdicionada = false;
			
			angular.forEach(revisao.teses, function(teseExistente) {
				if (teseExistente.codigo == tese.codigo) {
					teseAdicionada = true;
				}
			});
			
			if (!teseAdicionada){
				revisao.teses.push(tese);
			}
		};
		
		revisao.removerTese = function($index){
			
			for (var i = 0; i < $index.assuntos.length; i++){
				var indice = revisao.assuntos.indexOf($index.assuntos[i]);
				revisao.assuntos.splice(indice, 1);
			}
			
			revisao.teses.splice($index,1);
		};
		
		revisao.select2Options = {
			dropdownAutoWidth: 'true',
			minimumInputLength: 3,
	        quietMillis: 500,
			ajax : {
				url: properties.apiUrl + '/assuntos',
				dataType: 'json',
				data: function (term, page) {
					return {
				         termo: term, // search term
					};
				},
		        results: function (data, page) {
		        	
		        	var resultados = data.map(function(item){
		        			return {codigo:item.codigo, descricao : item.descricao};
		        	});
		        	return {results: resultados};
		        }
			},
	        formatResult: function(object, container, query) {
	        	return object.codigo + " - " + object.descricao;
			},
	        formatSelection: function (item) { 
	        	return item.descricao; 
	        }
		};

		$scope.$watch('revisao.assunto', function(novo){
			if (novo){
				revisao.adicionarAssuntoNaLista(novo);
			}
		});
		
		revisao.adicionarAssuntoNaLista = function(assunto){
			var verificaSeAssuntoExiste = false;
			angular.forEach(revisao.assuntos, function(assuntoS) {
				if (assuntoS.codigo == assunto.codigo) {
					verificaSeAssuntoExiste = true;
				}
			});
			if (!verificaSeAssuntoExiste){
				revisao.assuntos.push(assunto);
				revisao.assunto = null;
			}
		};
		
		revisao.removerAssunto = function($index){
			revisao.assuntos.splice($index,1);
		}
		
		revisao.validar = function() {
			var errors = null;
			
			if (revisao.assuntos.length == 0){
				errors = 'Nenhum assunto foi selecionado</b>.<br/>';
			}
			
			if (revisao.teses.length == 0){
				errors = 'Nenhuma tese foi selecionada</b>.<br/>';
			}
			
			if (revisao.observacao.length === 0) {
				errors = 'Você precisa escrever uma descrição da análise</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			
			revisao.recursos.push(new RevisarRepercussaoGeralCommand(revisao.processo.id, revisao.teses, revisao.assuntos, revisao.observacao));
			
			return true;
		}
		
		revisao.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + revisao.processo.classe + '/' + revisao.processo.numero + '</b> revisado com sucesso.');
		};

    	function RevisarRepercussaoGeralCommand(processoId, teses, assuntos, observacao){
    		var dto = {};
    		dto.processoId = processoId;
    		dto.teses = [];
    		dto.assuntos = [];
    		dto.observacao = observacao;
    		
    		angular.forEach(teses, function(tese) {
    			dto.teses.push(tese.codigo);
    		});
    		
    		angular.forEach(assuntos, function(assunto) {
    			dto.assuntos.push(assunto.codigo);
    		});
 
    		return dto;
    	}
	});

})();
