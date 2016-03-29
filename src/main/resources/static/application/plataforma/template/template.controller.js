(function($) {
	angular.plataforma.controller('TemplateController', function($scope, $state, ActionService, PesquisaService) {
		ActionService.load("autuacao");
		
		var pesquisaAnterior;
		$scope.pesquisas = [];
		
		var atualizarMinhasPesquisas = function() {
			PesquisaService.recuperarMinhasPesquisas()
			.then(function(result) {
				$scope.pesquisas = result.data;
			});
		};
		atualizarMinhasPesquisas();
		$scope.$on('atualizarMinhasPesquisas', atualizarMinhasPesquisas);
		
		$scope.carregarPesquisa = function(pesquisa) {
			var state = 'pesquisar.' + pesquisa.tipo.toLowerCase() + '.avancada';
			var params = { pesquisaId : pesquisa.id };
			var opts = { reload : true };
			$state.go(state, params, opts);
		};
		
	}).run(function($compile) {
		// Observa pela adição da sidebar no documento para só então ativar o plugin que a faz funcionar
		$('body').on('DOMNodeInserted', function(e) {
			$target = $(e.target);
			if ($target.is('[data-pages="sidebar"]')) {
		    	$.Pages.initSidebar();
		    }
		});
		
		$.Pages.init();
		
	});
})(jQuery);