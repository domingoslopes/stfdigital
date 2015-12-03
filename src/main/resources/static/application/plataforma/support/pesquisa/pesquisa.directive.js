/**
 * Diretiva que cria uma combo select2 que permite realizar pesquisas genéricas
 * sobre informação indexada
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	/**
	 * Cofigura um estado abstrato para registrar a url padrão para os
	 * estados de execução da ação.
	 * Os estados das ações devem ser configurados nos módulos:
	 * 
	 * $stateProvider.state('actions.contexto.acao', {
	 * 	// Configurar a view e controller de acordo com a especificação do ui-router
	 *  // A ação deve possuir o mesmo identificador definido no módulo do servidor
	 *  // O controlador pode acessar os recursos via $stateParams.resources
	 *	});
	 */
	angular.plataforma.config(['$stateProvider', function($stateProvider) {
		$stateProvider.state('root.pesquisa', {
			abstract : true,
			url : '/pesquisa'
		});
		
	}]);
	
	/**
	 * Diretiva que lista resultados de pesquisa
	 * Ex. de uso: 
	 * <pesquisa configuracao="{...}" />
	 */
	angular.plataforma.directive('pesquisa', ['properties', '$cookies', function (properties, $cookies) {
		return {
			restrict : 'E',
			scope : {
				modelo : '=',
				configuracao : '=' //Configuração da diretiva
			},
			link : function(scope, elem, attrs) {
				var conf = scope.configuracao;
				if (!angular.isObject(conf)) throw "Um objeto com a configuração deve ser informado!";
				
				var texto = null; //obrigatório, campo que representa o texto que será apresentado no retorno. Ex: 'identificacao'
				if(angular.isDefined(conf.texto)) {
					texto = conf.texto;
				} else { 
					throw 'Texto obrigatório! Um campo deve ser escolhido para ser exibido no resultado como texto.';
				}
				
				var indices = null; //obrigatório, indices a serem pesquisados. Ex: ['autuacao']
				if(angular.isArray(conf.indices)) {
					indices = conf.indices;
				} else { 
					throw 'Índices obrigatórios! Um array deve ser informado com os índices para pesquisa.';
				}
				
				var filtros = null; //obrigatório, filtros da pesquisa. Ex: ['identificacao']
				if(angular.isArray(conf.filtros)) {
					filtros = conf.filtros;
				} else { 
					throw 'Filtros obrigatórios! Os campos que devem ser filtrados pela entrada informada.';
				}
				var campos = (angular.isArray(conf.campos)) ? conf.campos : null; //opcional, campos as serem retornados. Ex: 
				var tipos = (angular.isArray(conf.tipos)) ? conf.tipos : null;  //opcional, os tipos de objetos que devem ser retornados. Ex: ['PeticaoFisica', 'Processo']
				var ordenadores = (angular.isObject(conf.ordenadores)) ? conf.ordenadores : null; //opcional, campos de ordenação. Ex: [{'identificacao' : 'ASC'},{'classe' : 'DESC'}] 
				var pesquisa = (angular.isDefined(conf.pesquisa)) ? conf.pesquisa : 'simples'; //opcional, o tipo de pesquisa que deve ser realizado. Ex: 'sugestao' ou 'paginada'
				var isPaginada = (pesquisa === 'paginada');
				var url = properties.apiUrl + '/pesquisas/';
				url += (pesquisa === 'sugestao') ? 'sugestoes' : ((pesquisa === 'paginada') ? 'paginadas' : ''); 
				
				$(elem).select2({
					dropdownAutoWidth: 'true',
			        minimumInputLength: 3,
			        quietMillis: 500,
			        placeholder: "Clique para pesquisar",
			        multiple: false,
			        allowClear: true,
			        ajax: {
			            url: url,
			            params: {
			                contentType: 'application/json'
			            },
			            dataType: 'json',
			            type: "POST",
			            data: function (term, page) {
			            	var pesquisa = {
			            		'indices': indices, 
		            			'filtros': {},
		            			'tipos': tipos,
		            			'campos': campos,
		            			'ordenadores': ordenadores,
		            		};
			            	if (isPaginada) {
			            		pesquisa.page = page - 1;
			            		pesquisa.tamanho = 15;
			            	}
		            		angular.forEach(filtros, function(filtro) {
		            			pesquisa.filtros[filtro] = [term.toLowerCase()];
		            		});
			            	return JSON.stringify(pesquisa);
			            },
				        results: function (data, page) {
			        		var itens = [];			        					        		
			        		angular.forEach(((isPaginada) ? data.content : data), function(item) {
			        			itens.push(item);
			        		});
			        		var results = {results: itens};
			        		if (isPaginada) {
			        			results.more = page <= data.page.totalPages;
			        		}
				        	return results;
				        },
			            transport: function(params){
			                params.beforeSend = function(request){
			                    request.setRequestHeader('X-XSRF-TOKEN', $cookies.get('XSRF-TOKEN'));
			                };
			                return $.ajax(params);
			            }
				    },
			        formatResult: function (item) { 
			        	return ('<div>' + item.objeto[texto] + '</div>'); 
			        },
			        formatSelection: function (item) { 
			        	return item.objeto[texto]; 
			        },
			        escapeMarkup: function (m) { 
			        	return m;
			        }
			    });
				$(elem).on('change', function(e) {
					scope.modelo = angular.isDefined(e.added) ? e.added : null;
					scope.$apply();
				});
				
				scope.$watch('modelo', function(novoValor){
					if (!novoValor){
						$(elem).select2('val','');
					}
				})
			}
		};
	}]);
})();
