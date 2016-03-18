/**
 * @author Anderson.Araujo
 * 
 * @since 28.01.2016
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AnaliseRepercussaoGeralController', function ($stateParams, messages, properties, ProcessoService) {
		var analise = this;
		
		var resource = $stateParams.resources[0];
		
		analise.assuntos = [];
		analise.tiposTeses = [{id:"CONTROVERSIA", descricao : "Controvérsia"}, 
		                 {id:"PRE_TEMA", descricao : "Pré-tema"},
		                 {id:"REPERCUSSAO_GERAL", descricao : "Repercussão Geral"}];
		analise.tipoTese = analise.tiposTeses[0];
		analise.numeroTese = '';
		analise.teses = [];
		analise.observacao = '';
		analise.recursos = [];
		
		if (angular.isObject(resource)) {
			if (angular.isDefined(resource.peticaoId)) {
				analise.id = resource.peticaoId;
			} else if (angular.isDefined(resource.processoId)) {
				analise.id = resource.processoId;
			}
		} else {
			analise.id = resource;
		}
		
		var consultarProcesso = null;
		analise.tarefa = $stateParams.task;
		
		if (analise.tarefa.metadado.tipoInformacao == 'ProcessoRecursal') {
			consultarProcesso = ProcessoService.consultar(analise.id);
		} else {
			consultarProcesso = ProcessoService.consultarPorPeticao(analise.id);
		}
		consultarProcesso.success(function(processo) {
			analise.processo = processo;
		});
		
		analise.selecionarTipoTese = function(tipo) {
			analise.tipoTese = tipo;
		};
		
		analise.consultarTese = function() {
			var soNumeros = /^[0-9]+$/g;
			
			if (!soNumeros.test(analise.numeroTese)) {
				return false;
			}
			
			ProcessoService.consultarTeses(analise.tipoTese.id, analise.numeroTese).then(function(resposta) {
				var teseConsultada = resposta.data[0];
				
				if (teseConsultada !== undefined){
					var assuntosConsultados = teseConsultada.assuntos;  
					
					analise.adicionarTese(teseConsultada);
					
					for (var i = 0; i < assuntosConsultados.length; i++) {
						analise.adicionarAssuntoNaLista({codigo : assuntosConsultados[i].codigo, descricao : assuntosConsultados[i].descricao, codigoTese : teseConsultada.codigo});
					}
					
					analise.numeroTese = null;
				} else {
					messages.error('Tese não encontrada');
				}
			}).catch(function(resposta) {
				messages.error(resposta.data.message);
			})
		};
		
		analise.adicionarTese = function(tese){
			var teseAdicionada = false;
			
			angular.forEach(analise.teses, function(teseExistente) {
				if (teseExistente.codigo == tese.codigo) {
					teseAdicionada = true;
				}
			});
			
			if (!teseAdicionada){
				analise.teses.push(tese);
			}
		};
		
		analise.removerTese = function($index){
			
			for (var i = 0; i < $index.assuntos.length; i++){
				var indice = analise.assuntos.indexOf($index.assuntos[i]);
				analise.assuntos.splice(indice, 1);
			}
			
			analise.teses.splice($index,1);
		};
		
		analise.select2Options = {
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
		        			return {id:item.codigo, codigo: item.codigo, descricao : item.descricao};
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

		$scope.$watch('analise.assunto', function(novo){
			if (novo){
				analise.adicionarAssuntoNaLista(novo);
			}
		});
		
		analise.adicionarAssuntoNaLista = function(assunto){
			var verificaSeAssuntoExiste = false;
			angular.forEach(analise.assuntos, function(assuntoS) {
				if (assuntoS.codigo == assunto.codigo) {
					verificaSeAssuntoExiste = true;
				}
			});
			if (!verificaSeAssuntoExiste){
				analise.assuntos.push(assunto);
				analise.assunto = null;
			}
		};
		
		analise.removerAssunto = function($index){
			analise.assuntos.splice($index,1);
		}
		
		analise.validar = function() {
			var errors = null;
			
			if (analise.assuntos.length == 0){
				errors = 'Nenhum assunto foi selecionado</b>.<br/>';
			}
			
			if (analise.teses.length == 0){
				errors = 'Nenhuma tese foi selecionada</b>.<br/>';
			}
			
			if (analise.observacao.length === 0) {
				errors = 'Você precisa escrever uma descrição da análise</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			
			analise.recursos.push(new AnalisarRepercussaoGeralCommand(analise.processo.id, analise.teses, analise.assuntos, analise.observacao));
			
			return true;
		}
		
		analise.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + analise.processo.classe + '/' + analise.processo.numero + '</b> analisado com sucesso.');
		};

    	function AnalisarRepercussaoGeralCommand(processoId, teses, assuntos, observacao){
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