(function($) {
	angular.plataforma.controller('TemplateController', function(ActionService) {
		ActionService.load("autuacao");
	}).run(function() {
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