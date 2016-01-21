/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('AutuacaoController', function ($scope, $log, $state, $stateParams, messages, properties, ClasseService, PeticaoService , PessoaService) {
		var autuacao = this;
		
		var resource = $stateParams.resources[0];
		autuacao.peticaoId = angular.isObject(resource) ? resource.peticaoId : resource;
		autuacao.classe = '';
		autuacao.valida = 'true';
		autuacao.motivo = '';
		autuacao.classes = [];
		autuacao.peticao = {};
		autuacao.recursos = [];
		autuacao.partesPoloAtivo = [];
		autuacao.partesPoloPassivo = [];
		var poloAtivo = {adicionados : [], removidos : []};
		var poloPassivo = {adicionados : [], removidos : []};
		autuacao.poloAtivoController = new PartesController(poloAtivo);
		autuacao.poloPassivoController = new PartesController(poloPassivo);
		
		
		ClasseService.listar().success(function(classes) {
			autuacao.classes = classes;
		});
		
		PeticaoService.consultar(autuacao.peticaoId).then(function(data) {
			autuacao.peticao = data;
			if (autuacao.peticao.partes.PoloAtivo.length > 0){
				angular.forEach(autuacao.peticao.partes.PoloAtivo, function(parteAtiva) {
					PessoaService.pesquisar(parteAtiva).then(function(dado){
						var pessoaAtivo = dado.data;
						autuacao.partesPoloAtivo.push({'id':parteAtiva, 'pessoa': pessoaAtivo});
					});
				});
			}
			if (autuacao.peticao.partes.PoloPassivo.length > 0){
				angular.forEach(autuacao.peticao.partes.PoloPassivo, function(partePassiva) {
					PessoaService.pesquisar(partePassiva).then(function(dado){
						var pessoaPassiva = dado.data;
						autuacao.partesPoloPassivo.push({'id':partePassiva, 'pessoa': pessoaPassiva});
					});
				});
			}
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
			autuacao.poloAtivoController.remover(parteSelecionada);
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
			autuacao.poloPassivoController.remover(parteSelecionada);
		};
        
        var adicionarPartePolo = function(polo){
            var parte = {'pessoa': {}};
            if (polo === 'ativo'){
                parte.pessoa.nome = autuacao.partePoloAtivo;
                autuacao.partesPoloAtivo.push(parte);
                autuacao.poloAtivoController.adicionar(parte);
                autuacao.partePoloAtivo = '';
                $('partePoloAtivo').focus();      
            }else{
                parte.pessoa.nome = autuacao.partePoloPassivo;
                autuacao.partesPoloPassivo.push(parte);
                autuacao.poloPassivoController.adicionar(parte);
                autuacao.partePoloPassivo = '';
                $('partePoloPassivo').focus();  
            }
        }
		
		autuacao.validar = function() {
			var errors = null;
			if (autuacao.classe.length === 0) {
				errors = 'Você precisa selecionar <b>a classe processual definitiva</b>.<br/>';
			}
			
			if (autuacao.valida === 'false' && autuacao.motivo.length === 0) {
				errors += 'Para petição inválidas, você precisa informar o motivo da inaptidão.<br/>';
			}
			
			if (errors) {
				messages.error(errors);
				return false;
			}
			autuacao.recursos.push(new AutuarCommand(autuacao.peticaoId, autuacao.classe, poloAtivo, poloPassivo, autuacao.valida, autuacao.motivo));
			return true;
		}
		
		autuacao.completar = function() {
			$state.go('dashboard');
			messages.success('Petição <b>' + autuacao.peticao.identificacao + '</b> autuada com sucesso.');
		};
		
	 	function PartesController(polo){
    		var partesController = {};
    		
    		partesController.adicionar = function(parte) {
				polo.adicionados.push({
					text : parte.pessoa.nome,
					//selected : false
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
				//parteSelecionada.selected = true;
				//var partesAtuais = polo.removidos.slice(0);
				//partesController.clear(partes.removidos);
/*				angular.forEach(partesAtuais, function(parte) {
					if (!parte.selected) {
						partes.removidos.push(parte);
					}
				});*/
			};
			
			partesController.clear = function(array) {
				while (array.length) {
					array.pop();
				}
			};
			return partesController;
		}
		

    	function AutuarCommand(id, classe, poloAtivo, poloPassivo, valida, motivo){
    		var dto = {};
    		dto.peticaoId = id;
    		dto.classeId = classe;
    		dto.poloAtivo = poloAtivo;
    		dto.poloPassivo = poloPassivo;
    		dto.valida = valida;
    		dto.motivo = motivo;
    		
    		return dto;
    	}
		
	});

})();

