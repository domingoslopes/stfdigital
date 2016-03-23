/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('EnvioProcessoController', function ($scope, $state, $stateParams, messages, properties, ClasseService, ProcessoService, OrigemService) {
		
		var envio = this;
		//var resource = $stateParams.resources[0];
		envio.classe = '';
		envio.classes = [];
		envio.sigilos = [{codigo: '1', nome: 'Público'}, {codigo: '2', nome: 'Segredo de Justiça'}];
		envio.sigilo = ''
		envio.preferenciasSelecionadas = [];
		envio.preferencias = [];
		envio.procedencias = [];
		envio.tribunalJuizos = [];
		envio.origens = [];
		envio.procedencia;
		envio.tribunalJuizo;
		envio.numeroOrigem;
		envio.assuntosSelecionados = []; 
		/*envio.partesPoloAtivo = [];
		envio.partesPoloPassivo = [];
		envio.poloAtivoController = new PartesController(envio.partesPoloAtivo);
		envio.poloPassivoController = new PartesController(envio.partesPoloPassivo); */

		
		ClasseService.listar().success(function(classes) {
			envio.classes = classes;
		});
		
		envio.carregarPreferencias = function() {
			ClasseService.consultarPreferencias(envio.classe).success(function(preferencias) {
				envio.preferenciasSelecionadas = [];
				envio.preferencias = preferencias;
			});
		};
		
		envio.carregarOrigens = function() {
			OrigemService.listarUnidadesFederacao(envio.procedencia).success(function(tribunalJuizos) {
				envio.tribunalJuizos = tribunalJuizos;
			});
		};
		
		envio.handle = function(e){
			if (e.keyCode === 13){
				var origemConcat = envio.tribunalJuizo + envio.numeroOrigem;
				origens.push(origemConcat);
			}
		};
		
		envio.removerOrigem = function(origem){
			var index = origens.indexOf(origem);
			if (index > -1) {
				origem.splice(index, 1);
			}
		};
		
		envio.select2Options = {
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
					return object.id + " - " + object.descricao;
				},
				formatSelection: function (item) { 
					return item.descricao; 
				}
		};
		
		$scope.$watch('envio.assunto', function(novo){
			if (novo){
				envio.adicionarAssuntoNaLista(novo);
			}
		});
		
		envio.adicionarAssuntoNaLista = function(assunto){
			var verificaSeAssuntoExiste = false;
			angular.forEach(envio.assuntosSelecionados, function(assuntoS) {
				if (assuntoS.id == assunto.id) {
					verificaSeAssuntoExiste = true;
				}
			});
			if (!verificaSeAssuntoExiste){
				envio.assuntosSelecionados.push(assunto);
				envio.assunto = null;
			}
		};
		
		envio.removerAssuntoSelecionadoLista = function($index){
			envio.assuntosSelecionados.splice($index,1);
		}
	/*	
		if (angular.isObject(resource)) {
			if (angular.isDefined(resource.peticaoId)) {
				envio.id = resource.peticaoId;
			} else if (angular.isDefined(resource.processoId)) {
				envio.id = resource.processoId;
			}
		} else {
			envio.id = resource;
		}
		
		var consultarProcesso = null;
		envio.tarefa = $stateParams.task;
		
		if (envio.tarefa.metadado.tipoInformacao == 'ProcessoRecursal') {
			consultarProcesso = ProcessoService.consultar(envio.id);
		} else {
			consultarProcesso = ProcessoService.consultarPorPeticao(envio.id);
		}
		consultarProcesso.success(function(processo){
			envio.processo = processo;
		});
		

		
		envio.adicionarPoloAtivo = function() {
			envio.poloAtivoController.adicionar(envio.partePoloAtivo);
			envio.partePoloAtivo = '';
			$('partePoloAtivo').focus();
		};
	
		envio.removerPoloAtivo = function(parteSelecionada) {
			envio.poloAtivoController.remover(parteSelecionada);
		};

		envio.adicionarPoloPassivo = function() {
			envio.poloPassivoController.adicionar(envio.partePoloPassivo);
			envio.partePoloPassivo = '';
			$('partePoloPassivo').focus();
		};
	
		envio.removerPoloPassivo = function(parteSelecionada) {
			envio.poloPassivoController.remover(parteSelecionada);
		};
		
		envio.validar = function() {
			var errors = null;
			
			if (envio.partesPoloAtivo.length === 0) {
				errors = 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>ativo</b>.<br/>';
			}
			
			if (envio.partesPoloPassivo.length === 0) {
				errors += 'Você precisa informar <b>pelo menos uma parte</b> para o polo <b>passivo</b>.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			envio.recursos.push( new AutuarProcessoCriminalEleitoralCommand(envio.processo.id, envio.partesPoloAtivo, envio.partesPoloPassivo, envio.assuntosSelecionados));
			return true;
		}
		
		envio.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + envio.processo.classe + '/' + envio.processo.numero + '</b> autuado com sucesso.');
		};
		
		
    	function PartesController(partes){
    		var partesController = {};
    		
    		partesController.adicionar = function(parte) {
				partes.push({
					text : parte,
					selected : false
				});
			};
		
			partesController.remover = function(index) {
				partes.splice(index, 1);
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
		*/
	});

})();

