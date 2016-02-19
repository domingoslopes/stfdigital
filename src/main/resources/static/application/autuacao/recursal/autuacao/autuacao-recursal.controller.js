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
		
		autuacao.poloAtivoController = new PartesController(autuacao.partesPoloAtivo);
		
		autuacao.poloPassivoController = new PartesController(autuacao.partesPoloPassivo);
		
		autuacao.valida = 'true';
		
		autuacao.recursos = [];
		
		ProcessoService.consultarPorPeticao(autuacao.peticaoId).then(function(response) {
			autuacao.processo = response.data;
			autuacao.motivosInaptidao = response.data.motivosInaptidao;
			autuacao.teses = response.data.teses;
			autuacao.assuntos = response.data.assuntos;
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
		
		PeticaoService.consultar(autuacao.peticaoId).then(function(data) {
			autuacao.peticao = data;
		});

		autuacao.adicionarPoloAtivo = function() {
			adicionarPartePolo('ativo');
		};
	
		autuacao.removerPoloAtivo = function(parteSelecionada) {
			angular.forEach(autuacao.partesPoloAtivo, function(parte, indice){
				if (parte.pessoa.nome === parteSelecionada.pessoa.nome){
					autuacao.partesPoloAtivo.splice(indice,1);
				}
			});
		};

		autuacao.adicionarPoloPassivo = function() {
			adicionarPartePolo('passivo');
		};
	
		autuacao.removerPoloPassivo = function(parteSelecionada) {
			angular.forEach(autuacao.partesPoloPassivo, function(parte, indice){
				if (parte.pessoa.nome === parteSelecionada.pessoa.nome){
					autuacao.partesPoloPassivo.splice(indice,1);
				}
			});
		};
        
        var adicionarPartePolo = function(polo){
            var parte = {'pessoa': {}};
            if (polo === 'ativo'){
                parte.pessoa.nome = autuacao.partePoloAtivo;
                autuacao.partesPoloAtivo.push(parte);
                autuacao.partePoloAtivo = '';
                $('partePoloAtivo').focus();      
            }else{
                parte.pessoa.nome = autuacao.partePoloPassivo;
                autuacao.partesPoloPassivo.push(parte);
                autuacao.partePoloPassivo = '';
                $('partePoloPassivo').focus();  
            }
        }
		

		
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

			autuacao.recursos.push( new AutuarProcessoRecursalCommand(autuacao.processo.id, autuacao.partesPoloAtivo, autuacao.partesPoloPassivo, autuacao.assuntos));

			return true;
		};
		
		autuacao.completar = function() {
			$state.go('dashboard');
			messages.success('Processo <b>' + autuacao.processo.classe + '/' + autuacao.processo.numero + '</b> autuado com sucesso.');
		};
		
		
	 	function PartesController(polo){
    		var partesController = {};
    		
    		partesController.adicionar = function(parte) {
				polo.adicionados.push({
					text : parte.pessoa.nome,
				});
			};
		
			partesController.remover = function(parteSelecionada) {
				if (parteSelecionada.id){
					polo.removidos.push(parteSelecionada);
				}else{
					for(var i in polo.adicionados){
						if (polo.adicionados[i].text === parteSelecionada.pessoa.nome){
							polo.adicionados.splice(i,1);
							break;
						}
					}
				}
			};
			
			partesController.clear = function(array) {
				while (array.length) {
					array.pop();
				}
			};
			return partesController;
		}

    	function AutuarProcessoRecursalCommand(id, partesPoloAtivo, partesPoloPassivo, assuntos){
    		var dto = {};
    		dto.processoId = id;
    		dto.partesPoloAtivo = [];
    		dto.partesPoloPassivo = [];
    		dto.assuntos = [];

    		angular.forEach(partesPoloAtivo, function(parte) {
    			dto.partesPoloAtivo.push(parte.pessoa.nome);
    		});
    		
    		angular.forEach(partesPoloPassivo, function(parte) {
    			dto.partesPoloPassivo.push(parte.pessoa.nome);
    		});
    		
    		angular.forEach(assuntos, function(assunto) {
    			dto.assuntos.push(assunto.codigo);
    		});

    		return dto;
    	}
		
	});
})();

