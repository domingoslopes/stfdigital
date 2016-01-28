/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoCriminalController', function ($scope, $log, $state, $stateParams, messages, properties, ProcessoService, PeticaoService) {
		var autuacao = this;
		
		var resource = $stateParams.resources[0];
		
		autuacao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		
		autuacao.classe = '';
		
		autuacao.partesPoloAtivo = [];
		
		autuacao.partesPoloPassivo = [];
		
		autuacao.poloAtivoController = new PartesController(autuacao.partesPoloAtivo);
		
		autuacao.poloPassivoController = new PartesController(autuacao.partesPoloPassivo);
		
		autuacao.valida = 'true';
		
		autuacao.motivo = '';
		
		autuacao.assuntosSelecionados = [];
		
		autuacao.recursos = [];
		
		ProcessoService.consultarProcessoPeloIdPeticao(autuacao.peticaoId).success(function(data){
			autuacao.processo = data;
		});
		
		PeticaoService.consultar(autuacao.peticaoId).then(function(data) {
			autuacao.peticao = data;
		});
		
		autuacao.select2Options = {
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
		        			return {id:item.codigo, descricao : item.descricao};
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

		$scope.$watch('autuacao.assunto', function(novo){
			if (novo){
				autuacao.adicionarAssuntoNaLista(novo);
			}
		});
		
		autuacao.adicionarAssuntoNaLista = function(assunto){
			var verificaSeAssuntoExiste = false;
			angular.forEach(autuacao.assuntosSelecionados, function(assuntoS) {
				if (assuntoS.id == assunto.id) {
					verificaSeAssuntoExiste = true;
				}
			});
			if (!verificaSeAssuntoExiste){
				autuacao.assuntosSelecionados.push(assunto);
				autuacao.assunto = null;
			}
		};
		
		autuacao.removerAssuntoSelecionadoLista = function($index){
			autuacao.assuntosSelecionados.splice($index,1);
		}
		
		autuacao.adicionarPoloAtivo = function() {
			autuacao.poloAtivoController.adicionar(autuacao.partePoloAtivo);
			autuacao.partePoloAtivo = '';
			$('partePoloAtivo').focus();
		};
	
		autuacao.removerPoloAtivo = function(parteSelecionada) {
			autuacao.poloAtivoController.remover(parteSelecionada);
		};

		autuacao.adicionarPoloPassivo = function() {
			autuacao.poloPassivoController.adicionar(autuacao.partePoloPassivo);
			autuacao.partePoloPassivo = '';
			$('partePoloPassivo').focus();
		};
	
		autuacao.removerPoloPassivo = function(parteSelecionada) {
			autuacao.poloPassivoController.remover(parteSelecionada);
		};
		
/*		PeticaoService.consultar(autuacao.peticaoId).then(function(data) {
			autuacao.peticao = data;
		});*/
		
		autuacao.validar = function() {
			var errors = null;
			
			if (autuacao.partesPoloAtivo.length === 0) {
				errors = 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>ativo</b>.<br/>';
			}
			
			if (autuacao.partesPoloPassivo.length === 0) {
				errors += 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>passivo</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			autuacao.recursos.push( new AutuarProcessoCriminalEleitoralCommand(autuacao.processo.id, autuacao.partesPoloAtivo, autuacao.partesPoloPassivo, autuacao.assuntosSelecionados));
			return true;
		}
		
		autuacao.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + autuacao.processo.classe + '/' + autuacao.processo.numero + '</b> autuado com sucesso.');
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

    	function AutuarProcessoCriminalEleitoralCommand(id, partesPoloAtivo, partesPoloPassivo, assuntosSelecionados){
    		var dto = {};
    		dto.processoId = id;
    		dto.partesPoloAtivo = [];
    		dto.partesPoloPassivo = [];
    		dto.assuntos = [];
    		
    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.text);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.text);
    		});
    		
    		angular.forEach(assuntosSelecionados, function(assunto) {
    			dto.assuntos.push(assunto.id);
    		});
    		return dto;
    	}
		
	});

})();

