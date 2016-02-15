/**
 * @author Anderson.Araujo
 * 
 * @since 01.02.2016
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoRecursalController', function ($scope, $log, $state, $stateParams, messages, properties, ProcessoService, PeticaoService) {
		var autuacao = this;
		
		var resource = $stateParams.resources[0];
		
		autuacao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		autuacao.classe = '';
		autuacao.partesPoloAtivo = [];
		autuacao.partesPoloPassivo = [];
		autuacao.teses = [];
		autuacao.assuntos = [];
		asutuacao.motivos = 
		autuacao.poloAtivoController = new PartesController(autuacao.partesPoloAtivo);
		autuacao.poloPassivoController = new PartesController(autuacao.partesPoloPassivo);
		autuacao.valida = 'true';
		autuacao.recursos = [];
		
		ProcessoService.consultarProcessoPeloIdPeticao(autuacao.peticaoId).success(function(data){
			autuacao.processo = data;
			autuacao.teses = autuacao.processo.teses;
			autuacao.assuntos = autuacao.processo.assuntos;
			autuacao.motivos = autuacao.processo.motivos;
			
			if (autuacao.processo.partes.PoloAtivo.length > 0){
				angular.forEach(autuacao.peticao.partes.PoloAtivo, function(parteAtiva) {
					PessoaService.pesquisar(parteAtiva).then(function(dado){
						var pessoaAtivo = dado.data;
						autuacao.partesPoloAtivo.push({'id':parteAtiva, 'pessoa': pessoaAtivo});
					});
				});
			}
			
			if (autuacao.processo.partes.PoloPassivo.length > 0){
				angular.forEach(autuacao.peticao.partes.PoloPassivo, function(partePassiva) {
					PessoaService.pesquisar(partePassiva).then(function(dado){
						var pessoaPassiva = dado.data;
						autuacao.partesPoloPassivo.push({'id':partePassiva, 'pessoa': pessoaPassiva});
					});
				});
			}
			
		});
		
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