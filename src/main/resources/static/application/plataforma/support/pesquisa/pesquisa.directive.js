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
	 * Diretiva de lista de incidentes
	 * Ex. de uso: 
	 * <actions resources="recursos" type="Long"
	 *  context="autuacao" btn-class="btn-default" /> 
	 */
	angular.plataforma.directive('pesquisa', ['properties', function (properties) {
		return {
			restrict : 'E',
			scope : {
				indices : '@', //obrigatório, indices a serem pesquisados
				filtros : '@', //obrigatório, mapa com os filtros dos campos
				texto: '@', //obrigatório, propriedade que representa o texto que será apresentado
				campos : '@', //opcional, campos as serem retornados
				ordenador: '@' //opcional, campo de ordenação
			},
			link : function(scope, elem, attrs) {
				elem.select2({
			        minimumInputLength: 3,
			        multiple: false,
			        allowClear: true,
			        ajax: {
			            url: properties.apiUrl + /*'/' + context + */'/pesquisa',
			            dataType: 'json',
			            type: "POST",
			            data: function (term, page) { 
			            	return { indices: scope.indices, 
			            			 filtros: {identificacao: term},
			            			 campos: scope.campos,
			            			 ordenador: scope.ordenador};
			            	},
				        results: function (data, page) {
				        	
			        		var dataToReturn = [];
			        		angular.forEach(data, function(d) {
			        			var id = d.id;
				        		var text = d[scope.texto];
			        			dataToReturn.push({id : id, text: text});
			        		});
			        		
				        	return {results: dataToReturn};
				        }
				    },
			        initSelection: function (item, callback) {
			            var id = item.val();
			            var text = item.data('option');
			            var data = { id: id, text: text };
			            callback(data);
			        },
			        formatResult: function (item) { return ('<div>' + item.text + '</div>'); },
			        formatSelection: function (item) { return (item.text); },
			        escapeMarkup: function (m) { return m; }
			    });
			}
		}}
	]);
});