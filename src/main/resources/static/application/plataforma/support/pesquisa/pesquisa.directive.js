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
	 * Diretiva que lista resultados de pesquisa
	 * Ex. de uso: 
	 * <pesquisa campos="['identificacao', 'ministro.relator']" indices="['autuacao']"
	 *  filtros="'identificacao'" ordernador="'identificacao'" />
	 */
	angular.plataforma.directive('pesquisa', ['properties', function (properties) {
		return {
			restrict : 'AE',
			scope : {
				indices : '=', //obrigatório, indices a serem pesquisados
				filtros : '=', //obrigatório, mapa com os filtros dos campos
				texto: '=', //obrigatório, propriedade que representa o texto que será apresentado
				campos : '=', //opcional, campos as serem retornados
				ordenador: '=' //opcional, campo de ordenação
			},
			link : function(scope, elem, attrs) {
				$(elem).select2({
					dropdownAutoWidth: 'true',
			        minimumInputLength: 3,
			        quietMillis: 500,
			        placeholder: "Clique para pesquisar",
			        language : {
			        	formatInputTooShort: function (input, min) { 
			        		var n = min - input.length; 
			        		return "Please enter " + n + " more character" + (n == 1? "" : "s"); }
			        },
			        multiple: false,
			        allowClear: true,
			        ajax: {
			            url: properties.apiUrl + /*'/' + context + */'/pesquisas',
			            params: {
			                contentType: 'application/json'
			            },
			            dataType: 'json',
			            type: "POST",
			            data: function (term, page) {
			            	//TODO: Corrigir filtro
			            	return JSON.stringify({ 'indices': scope.indices, 
			            			 'filtros': {identificacao: term},
			            			 'campos': scope.campos,
			            			 'ordenador': scope.ordenador});
			            	},
				        results: function (data, page) {
				        	
			        		var dataToReturn = [];
			        		angular.forEach(data, function(d) {
			        			var id = d.id;
				        		var text = d.objeto[scope.texto];
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
			        formatResult: function (item) { return ('<div class="p-r-10">' + item.text + '</div>'); },
			        formatSelection: function (item) { return (item.text); },
			        escapeMarkup: function (m) { return m; }
			    });
				$('.select2-drop').css('z-index', 800);
			}
		}}
	]);
})();