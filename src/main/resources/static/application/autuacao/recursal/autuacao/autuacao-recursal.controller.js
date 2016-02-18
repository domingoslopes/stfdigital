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
		
		autuacao.classe = '';
		
		autuacao.partesPoloAtivo = [];
		
		autuacao.partesPoloPassivo = [];
		
		autuacao.poloAtivoController = new PartesController(autuacao.partesPoloAtivo);
		
		autuacao.poloPassivoController = new PartesController(autuacao.partesPoloPassivo);
		
		autuacao.valida = 'true';
		
		autuacao.recursos = [];
		
		ProcessoService.consultarPorPeticao(revisao.peticaoId).then(function(response) {
			autuacao.processo = response.data;
			autuacao.motivosInaptidao = response.data.motivosInaptidao;
			autuacao.teses = response.data.teses;
			autuacao.assuntos = response.data.assuntos;
		});
		
		PeticaoService.consultar(revisao.peticaoId).then(function(data) {
			revisao.peticao = data;
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

			autuacao.recursos.push( new AutuarProcessoRecursalCommand(autuacao.processo.id, autuacao.partesPoloAtivo, autuacao.partesPoloPassivo));

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
		
			partesController.remover = function(index) {
				partes.splice(index, 1);
				
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


    	function AutuarProcessoCriminalEleitoralCommand(id, partesPoloAtivo, partesPoloPassivo){
=
    		dto.processoId = id;
    		dto.partesPoloAtivo = [];
    		dto.partesPoloPassivo = [];
    		
    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.text);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.text);
    		});
    		

    		return dto;
    	}
		
	});
})();

