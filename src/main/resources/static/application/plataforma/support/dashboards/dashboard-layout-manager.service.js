/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';

	/**
	 * @ngdoc service
	 * @name DashboardLayoutManager
	 * @memberOf plataforma
	 * 
	 * @description Service para fornecer funcionalidade de layout para dashboards.
	 * 
	 */	
	angular.plataforma.service('DashboardLayoutManager', [function() {

		/**
		 * @function defaultLayout
		 * @memberOf DashboardLayoutManager
		 * 
		 * @description Recupera o layout padrão, que por enquanto consiste em um
		 * layout tabular de duas colunas.
		 * 
		 */
		this.defaultLayout = function(dashlets) {
			var layout = {
				linhas: []
			};
			if (dashlets && dashlets.length > 0) {
				var linhaAtual, columnClasses;
				for (var i = 0; i < dashlets.length; i++) { // Monta um layout tabular de duas colunas.
					if (i % 2 === 0) {
						linhaAtual = {
							colunas: []
						};
						layout.linhas.push(linhaAtual);
						linhaAtual.colunas.push({dashlet: dashlets[i], columnClasses: ['col-md-6', 'col-lg-6', 'hidden-xlg m-b-10']});
					} else {
						linhaAtual.colunas.push({dashlet: dashlets[i], columnClasses: ['col-md-6', 'col-lg-6', 'hidden-xlg m-b-10']});
					}
				}
			}
			return layout;
		};
		
	}]);
})();
