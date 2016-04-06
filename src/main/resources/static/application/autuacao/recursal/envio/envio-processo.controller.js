/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.controller('EnvioProcessoController', function ($scope, $state, $stateParams, messages, properties, ClasseService, ProcessoService, OrigemService, AssuntoService) {
		
		var envio = this;
		envio.classeId = '';
		envio.classes = [];
		envio.sigilos = [{codigo: 'PUBLICO', nome: 'Público'}, {codigo: 'SEGREDO_JUSTICA', nome: 'Segredo de Justiça'}];
		envio.sigilo = '';
		envio.numeroRecursos = 0;
		envio.preferenciasSelecionadas = [];
		envio.preferencias = [];
		envio.procedencias = [];
		envio.tribunaisJuizos = [];
		envio.origens = [];
		envio.origensAdicionadas = [];
		envio.procedencia = '';
		envio.tribunalJuizo = '';
		envio.numeroOrigem = '';
		envio.assuntosAdicionados = [];
		envio.assuntoInformado = '';
		envio.partesPoloAtivo = [];
		envio.partesPoloPassivo = [];
		envio.parteInformada = '';
		envio.poloSelecionado = 'AT';
		envio.recursos = [];
		envio.chaveProcesso = $stateParams.resources[0];
		
		ClasseService.listar().success(function(classes) {
			envio.classes = classes;
		});
		
		envio.carregarPreferencias = function() {
			if ('' != envio.classeId){
				ClasseService.consultarPreferencias(envio.classeId).success(function(data) {
					envio.preferencias = data;
				});
			}
		};
		
		OrigemService.listarUnidadesFederacao().success(function(data) {
			envio.procedencias = data;
		});
		
		
		envio.carregarOrigens = function() {
			OrigemService.consultarTribunaisJuizos(envio.procedencia).success(function(data) {
				envio.tribunaisJuizos = data;
			});
		};
		
		envio.carregarProcessoSalvo = function(chave){
			var processo = '';
			
			if ('' != chave){
				processo = JSON.parse(localStorage[chave]);
				
				envio.classeId = processo.classeId;
				envio.sigilo = processo.sigilo;
				envio.numeroRecursos = processo.numeroRecursos;
				envio.carregarPreferencias();
				envio.preferenciasSelecionadas = processo.preferencias;
				envio.origensAdicionadas = processo.origensAdicionadas;
				envio.origens = processo.origens;
				envio.assuntosAdicionados = processo.assuntos;
				envio.partesPoloAtivo = processo.partesPoloAtivo; 
				envio.partesPoloPassivo = processo.partesPoloPassivo;
			}
		};
		
		envio.carregarProcessoSalvo(envio.chaveProcesso);
		
		envio.marcarOrigemPrincipal = function(indice){
			if (envio.origensAdicionadas[indice].isPrincipal){
				envio.origensAdicionadas[indice].isPrincipal = false;
			} else {
				envio.origensAdicionadas[indice].isPrincipal = true;
			}
			
			return envio.origensAdicionadas[indice].isPrincipal;
		};
		
		envio.isProcedenciaSelecionada = function(){
			return envio.procedencia != '';
		};
		
		envio.adicionarOrigem = function() {
			var erros = '';
			
			if (envio.procedencia === '' || envio.procedencia == null){
				erros = 'Informe a procedência do processo. <br />'
			}
			
			if (envio.tribunalJuizo === '' || envio.tribunalJuizo == null){
				erros += 'Informe o tribunal ou juízo de origem do processo. <br />'
			}
			
			if (envio.numeroOrigem === '' || envio.numeroOrigem == null){
				erros += 'Informe o nº do processo de origem.'
			}
			
			if (erros != ''){
				messages.error(erros);
				return false;
			}
			
			var origemConcat = getNomeObjeto(envio.tribunaisJuizos, 'codigo', envio.tribunalJuizo) + ' ' + getNomeObjeto(envio.procedencias, 'id', envio.procedencia) + ' ' + envio.numeroOrigem;
			envio.origens.push(origemConcat);
			
			var origemAdicionada = {unidadeFederacaoId:envio.procedencia, codigoJuizoOrigem:envio.tribunalJuizo, numeroProcesso:envio.numeroOrigem, numeroOrdem:1, isPrincipal:false};
			envio.origensAdicionadas.push(origemAdicionada);
			
			envio.procedencia = '';
			envio.tribunalJuizo = '';
			envio.numeroOrigem = '';
		};
		
		envio.verificarOrigemPrincipal = function(indice){
			return envio.origensAdicionadas[indice].isPrincipal;
		};
		
		envio.removerOrigem = function(origem){
			var index = envio.origens.indexOf(origem);
			if (index > -1) {
				envio.origens.splice(index, 1);
				envio.origensAdicionadas.splice(index, 1);
			}
		};
		
		function getNomeObjeto(array, key, value) {
		    for (var i = 0; i < array.length; i++) {
		        if (array[i][key] == value) {
		            return array[i].nome;
		        }
		    }
		    return null;
		};
		
		envio.pesquisarAssunto = function(){
			var verificadorNumero = /^[0-9]+$/g;
			
			if (verificadorNumero.test(envio.assuntoInformado)){
				AssuntoService.listar(envio.assuntoInformado).success(function(data){
					if (data.length > 0){
						adicionarAssunto(data[0]);
					} else {
						messages.error('Não existe assunto com o código ' + envio.assuntoInformado);
					}
				});
			} else {
				if (envio.assuntoInformado.length < 3) {
					messages.error('Informe ao menos 3 caracteres.');
				} else {
					AssuntoService.listar(envio.assuntoInformado).success(function(assuntosPesquisados){
						if (assuntosPesquisados.length > 0){
							envio.assuntosSelecionados = assuntosPesquisados;
						} else {
							messages.error('Nenhum assunto encontrado com o termo pesquisado.');
						}
					});	
				}
			}
		};
		
		envio.vincularAssunto = function (assunto) {
			adicionarAssunto(assunto);
			limparPesquisa();
        };
        
        var limparPesquisa = function () {
            envio.assuntosSelecionados = null;
            envio.assuntoInformado = '';
            $("#txtPesquisarAssunto").focus();
        };
        
        var limparCampos = function(){
        	envio.classeId = '';
    		envio.classes = [];
    		envio.sigilos = [{codigo: 'PUBLICO', nome: 'Público'}, {codigo: 'SEGREDO_JUSTICA', nome: 'Segredo de Justiça'}];
    		envio.sigilo = '';
    		envio.numeroRecursos = 0;
    		envio.preferenciasSelecionadas = [];
    		envio.preferencias = [];
    		envio.procedencias = [];
    		envio.tribunaisJuizos = [];
    		envio.origens = [];
    		envio.origensAdicionadas = [];
    		envio.procedencia = '';
    		envio.tribunalJuizo = '';
    		envio.numeroOrigem = '';
    		envio.assuntosSelecionados = [];
    		envio.assuntosAdicionados = [];
    		envio.assuntoInformado = '';
    		envio.partesPoloAtivo = [];
    		envio.partesPoloPassivo = [];
    		envio.parteInformada = '';
    		envio.poloSelecionado = 'AT';
    		envio.recursos = [];
        };
		
		var adicionarAssunto = function(assunto){
			var isAssuntoAdicionado = false;
			
			angular.forEach(envio.assuntosAdicionados, function(assuntoS) {
				if (assuntoS.codigo == assunto.codigo) {
					isAssuntoAdicionado = true;
				}
			});
			
			if (!isAssuntoAdicionado){
				envio.assuntosAdicionados.push(assunto);
				envio.assuntoInformado = '';
			} else {
				messages.error('Assunto já adicionado à lista.');
			}
		};
		
		envio.removerAssunto = function(assunto){
			var indice = 0;
			
			for (var i = 0; i < envio.assuntosAdicionados.length; i++){
				if (assunto.codigo == envio.assuntosAdicionados[i].codigo){
					indice = i;
					break;
				}
			}
			
			envio.assuntosAdicionados.splice(indice,1);
		};
		
		envio.adicionarParte = function(){
			if (envio.parteInformada == ''){
				messages.error('Parte não informada.');
				return;
			}
			
			switch(envio.poloSelecionado){
			case 'AT':
				adicionarPartePoloAtivo(envio.parteInformada.toUpperCase())
				break;
			case 'PA':
				adicionarPartePoloPassivo(envio.parteInformada.toUpperCase())
				break;
			default:
				messages.error('Informe o polo da parte.');
			}
			
			envio.parteInformada = '';
		};
		
		var adicionarPartePoloAtivo = function(parteAdicionada){
			var isParteAdicionada = false;
			
			for (var i = 0; i < envio.partesPoloAtivo.length; i++){
				if (parteAdicionada == envio.partesPoloAtivo[i]) {
					isParteAdicionada = true;
					break;
				}
			}
			
			if (isParteAdicionada){
				messages.error('A parte já foi adicionada ao polo ativo.');
			} else {
				envio.partesPoloAtivo.push(parteAdicionada);
			}
		};
		
		var adicionarPartePoloPassivo = function(parteAdicionada){
			var isParteAdicionada = false;
			
			for (var i = 0; i < envio.partesPoloPassivo.length; i++){
				if (parteAdicionada == envio.partesPoloPassivo[i]) {
					isParteAdicionada = true;
					break;
				}
			}
			
			if (isParteAdicionada){
				messages.error('A parte já foi adicionada ao polo passivo.');
			} else {
				envio.partesPoloPassivo.push(parteAdicionada);
			}
		};
		
		envio.removerPartePoloAtivo = function(parte){
			var indice = 0;
			
			for (var i = 0; i < envio.partesPoloAtivo.length; i++){
				if (parte == envio.partesPoloAtivo[i]){
					indice = i;
					break;
				}
			}
			
			envio.partesPoloAtivo.splice(indice,1);
		};
		
		envio.removerPartePoloPassivo = function(parte){
			var indice = 0;
			
			for (var i = 0; i < envio.partesPoloPassivo.length; i++){
				if (parte == envio.partesPoloPassivo[i]){
					indice = i;
					break;
				}
			}
			
			envio.partesPoloPassivo.splice(indice,1);
		};
		
		envio.setPolo = function(polo){
			envio.poloSelecionado = polo;
			$("#txtPesquisaParte").focus();
		};
		
		envio.validar = function(){
			var erros = 'Campo(s) não informado(s): ';
			
			if (envio.classeId == ''){
				erros += 'classe, ';
			}
			
			if (envio.sigilo == ''){
				erros += 'sigilo, '
			}
			
			if (envio.numeroRecursos < 0){
				erros += 'recurso, ';
			}
			
			if (envio.preferenciasSelecionadas.length === 0){
				erros += 'preferências, '
			}
			
			if (envio.origens.length === 0){
				erros += 'origem(s), ';
			}
			
			if (envio.assuntosAdicionados.length === 0){
				erros += 'assunto(s), '
			}
			
			if (envio.partesPoloAtivo.length === 0){
				erros += 'parte(s) do polo ativo, ';
			}
			
			if (envio.partesPoloPassivo.length === 0){
				erros += 'parte(s) do polo passivo, ';
			}
			
			if (erros == ''){
				messages.error(erros.substring(0, erros.length - 2));
				return false;
			}
			
			envio.recursos[0] = new EnviarProcessoCommand(envio.classeId, envio.sigilo, envio.numeroRecursos, envio.preferenciasSelecionadas, 
					criarListaDtosOrigem(envio.origensAdicionadas), envio.assuntosAdicionados, envio.partesPoloAtivo, envio.partesPoloPassivo);
			limparCampos();
			
			if ('' != envio.chaveProcesso){
				localStorage.removeItem(envio.chaveProcesso);
			}
			
			return true;
		};
		
		envio.completar = function() {
	    	$state.go('consultar-processo-envio');
	    	messages.success('Processo enviado com sucesso.');
	    };
	    
	    envio.salvar = function(){
	    	var processo = new criarProcessoLocal(envio.classeId, envio.sigilo, envio.numeroRecursos, envio.preferenciasSelecionadas, 
	    			envio.origens, envio.origensAdicionadas, envio.assuntosAdicionados, envio.partesPoloAtivo, envio.partesPoloPassivo);
	    	
	    	var existe = localStorage.getItem(envio.chaveProcesso);
	    		    	
	    	if ('' == existe || 'undefined' == existe || null == existe){
		    	localStorage.setItem("Processo " + envio.classeId + " - " + envio.sigilo + " - " + envio.numeroRecursos, JSON.stringify(processo));
	    	} else {
	    		localStorage.setItem(envio.chaveProcesso, JSON.stringify(processo));
	    	}
	    	
	    	limparCampos();
	    	$state.go('consultar-processo-envio');
	    	messages.success('O processo foi salvo com sucesso.');
	    };
	    
	    var excluirProcessoLocal = function(chave){
	    	localStorage.removeItem(chave);
	    };
		
		function EnviarProcessoCommand(classeId, sigilo, numeroRecursos, preferencias, origens, assuntos, partesPoloAtivo, partesPoloPassivo){
    		var dto = {};
    		dto.classeId = classeId;
    		dto.sigilo = sigilo;
    		dto.numeroRecursos = numeroRecursos;
    		dto.preferencias = preferencias;
    		dto.origens = origens;
    		dto.assuntos = [];
    		
    		for(var i = 0; i < assuntos.length; i++){
    			dto.assuntos.push(assuntos[i].codigo);
    		}
    		
    		dto.partesPoloAtivo = partesPoloAtivo;
    		dto.partesPoloPassivo = partesPoloPassivo;
    		
    		return dto;
    	};
    	
    	function criarListaDtosOrigem(origens) {
    		var origensDto = [];
    		
    		for (var i = 0; i < origens.length; i++) {
    			var dto = {};
    			
    			dto.unidadeFederacaoId = origens[i].unidadeFederacaoId;
    			dto.codigoJuizoOrigem = origens[i].codigoJuizoOrigem;
    			dto.numeroProcesso = origens[i].numeroProcesso;
    			dto.numeroOrdem = origens[i].numeroOrdem;
    			dto.isPrincipal = origens[i].isPrincipal;
    			        		
    			origensDto.push(dto);
    		}
    		
    		return origensDto;
    	};
    	
    	function criarProcessoLocal(classeId, sigilo, numeroRecursos, preferencias, origens, origensAdicionadas, assuntos, partesPoloAtivo, partesPoloPassivo){
    		var processo = {};
    		processo.classeId = classeId;
    		processo.sigilo = sigilo;
    		processo.numeroRecursos = numeroRecursos;
    		processo.preferencias = preferencias;
    		processo.origens = origens;
    		processo.origensAdicionadas = origensAdicionadas;
    		processo.assuntos = assuntos;
    		processo.partesPoloAtivo = partesPoloAtivo;
    		processo.partesPoloPassivo = partesPoloPassivo;
    		
    		return processo;
    	};
	});

})();

