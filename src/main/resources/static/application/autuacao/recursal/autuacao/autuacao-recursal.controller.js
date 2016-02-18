/**
<<<<<<< HEAD
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
=======
 * @author Anderson.Araujo
 * 
 * @since 01.02.2016
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoRecursalController', function ($scope, $log, $state, $stateParams, messages, properties, ProcessoService, PeticaoService) {
		var autuacao = this;
		
		var resource = $stateParams.resources[0];
		
		autuacao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
<<<<<<< HEAD
		
		autuacao.classe = '';
		
		autuacao.partesPoloAtivo = [];
		
		autuacao.partesPoloPassivo = [];
		
		autuacao.poloAtivoController = new PartesController(autuacao.partesPoloAtivo);
		
		autuacao.poloPassivoController = new PartesController(autuacao.partesPoloPassivo);
		
		autuacao.valida = 'true';
		
		autuacao.motivo = '';
		
		autuacao.assuntosSelecionados = [];
		
		autuacao.recursos = [];
		
		ProcessoService.consultarPorPeticao(revisao.peticaoId).then(function(response) {
			autuacao.processo = response.data;
			autuacao.motivosInaptidao = response.data.;
			autuacao.teses = response.data.teses;
			autuacao.assuntos = response.data.assuntos;
		});
		
		PeticaoService.consultar(revisao.peticaoId).then(function(data) {
			revisao.peticao = data;
		});
		
		
=======
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
		
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
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
<<<<<<< HEAD

=======
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
		
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
<<<<<<< HEAD
			autuacao.recursos.push( new AutuarProcessoRecursalCommand(autuacao.processo.id, autuacao.partesPoloAtivo, autuacao.partesPoloPassivo));
=======
			autuacao.recursos.push( new AutuarProcessoCriminalEleitoralCommand(autuacao.processo.id, autuacao.partesPoloAtivo, autuacao.partesPoloPassivo, autuacao.assuntosSelecionados));
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
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
		
<<<<<<< HEAD
			partesController.remover = function(index) {
				partes.splice(index, 1);
=======
			partesController.remover = function(parteSelecionada) {
				parteSelecionada.selected = true;
				var partesAtuais = partes.slice(0);
				partesController.clear(partes);
				angular.forEach(partesAtuais, function(parte) {
					if (!parte.selected) {
						partes.push(parte);
					}
				});
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
			};
			
			partesController.clear = function(array) {
				while (array.length) {
					array.pop();
				}
			};
			return partesController;
		}

<<<<<<< HEAD
    	function AutuarProcessoCriminalEleitoralCommand(id, partesPoloAtivo, partesPoloPassivo){
=======
    	function AutuarProcessoCriminalEleitoralCommand(id, partesPoloAtivo, partesPoloPassivo, assuntosSelecionados){
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
    		var dto = {};
    		dto.processoId = id;
    		dto.partesPoloAtivo = [];
    		dto.partesPoloPassivo = [];
<<<<<<< HEAD
=======
    		dto.assuntos = [];
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
    		
    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.text);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.text);
    		});
    		
<<<<<<< HEAD
=======
    		angular.forEach(assuntosSelecionados, function(assunto) {
    			dto.assuntos.push(assunto.id);
    		});
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
    		return dto;
    	}
		
	});

<<<<<<< HEAD
})();

=======
})();
>>>>>>> 15d6c1446c8216c7d7b3891a51f9e52ed8c2d9c6
