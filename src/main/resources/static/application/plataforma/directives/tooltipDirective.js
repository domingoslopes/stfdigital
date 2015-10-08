/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.plataforma.directive('stfTooltip', function() {
		return {
			scope: {
				stfTooltip: '=',
				stfCondition: '&' // Fun��o de callback para decidir se o tooltip fica ativo ou n�o.
			},
			link: function(scope, element, attrs) {
				var options = {};
				
				if(attrs.tooltipDelay && !isNaN(parseInt(attrs.tooltipDelay))){
					angular.extend(options, {delay: parseInt(attrs.tooltipDelay)});
				}
				
				element.tooltip(options);
				
				// Permite que o tooltip seja condicional, caso o atributo stfCondition seja setado (� uma fun��o de callback)
				if (attrs.stfCondition) {
					scope.$watch('stfCondition()', function(val) {
						if (val) {
							element.tooltip('enable');
						} else {
							element.tooltip('disable');
						}
					});
				}
			}
		};
	});
	
	/**
	 * Diretiva que permite adicionar um tooltip no select2.
	 * Ela espera que o formatSelection crie um elemento com o atributo
	 * data-original-title setado para o t�tulo da tooltip.
	 */
	directives.directive('stfSelect2TooltipSelected', function() {
		return {
			link: function(scope, element, attrs) {
				element.on('change', function(e) {
					var chosen = $(this).prev().find('.select2-choice');
					var options = {};
					
					if(attrs.tooltipDelay && !isNaN(parseInt(attrs.tooltipDelay))){
						angular.extend(options, {delay: parseInt(attrs.tooltipDelay)});
					}
					
					var titleTarget = chosen.find('[data-original-title]');
					
					chosen.attr('data-placement', 'top');
					
					if (titleTarget.attr('data-original-title')) {
						chosen.attr('data-original-title', titleTarget.attr('data-original-title'));
					}
					
					chosen.tooltip(options);
				});
			}
		};
	});
	
	directives.directive('stfSelect2Tooltip', function() {
		return {
			link: function(scope, element, attrs) {
				scope.$watch(function() {
					return element.prev().find('.select2-choice').length != 0;
				}, function(val) {
					if (val) {
						var chosen = element.prev().find('.select2-choice');
						var options = {};
						
						if(attrs.tooltipDelay && !isNaN(parseInt(attrs.tooltipDelay))){
							angular.extend(options, {delay: parseInt(attrs.tooltipDelay)});
						}
						
						chosen.attr('data-placement', 'top');
						chosen.attr('data-original-title', attrs.stfTextoTooltip);
						
						chosen.tooltip(options);
						scope.$watch(function() {
							return attrs.stfSelect2Tooltip;
						}, function(val) {
							if (val == 'true') {
								chosen.tooltip('enable');
							} else {
								chosen.tooltip('disable');
							}
						});
					}
				});
			}
		}
	});
});