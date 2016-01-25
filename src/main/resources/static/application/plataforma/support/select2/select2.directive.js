/**
 * Diretiva que permite a seleção de um valor defaul para o select2.
 * 
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	/**
	 * Diretiva de lista de ações
	 * Ex. de uso: 
	 * <select select2-default value="registro.tipoProcesso.id" id="tipoProcesso" 
	 * ng-model="registro.tipoProcesso" ng-options="tipo.nome for tipo in registro.tiposProcessos track by tipo.id">
	 *  
	 */
	angular.plataforma.directive('select2Default', [function () {
		return {
			restrict : 'AE',
			scope : {
				value : '=' 
			},
			link : function(scope, elem) {
				var select2 = $(elem).select2();
				setTimeout(function() {
					select2.select2('val', scope.value);
					scope.$apply();
				}, 100);
				
			}
		};
	}]);
})();

