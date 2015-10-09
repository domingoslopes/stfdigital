/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.plataforma.directive('stfModelMapper', function() {
		return {
			require: 'ngModel',
			scope: {
				collection: '=stfModelMapperCollection',
				idField: '@stfModelMapperIdField',
				mappedModel: '=stfModelMapper'
			},
			link: function(scope, element, attrs, ngModel) {
				ngModel.$parsers.push(function(valor) {
					return parseInt(valor);
				});
				
				scope.$watch(function () {
					return ngModel.$modelValue;
				}, function(newValue) {
					if (newValue) {
						for (var i in scope.collection) {
							if (scope.collection[i][scope.idField] == newValue) {
								for (var property in scope.mappedModel) {
									if (property != scope.idField) {
										delete scope.mappedModel[property];
									}
									
								}
								for (var propertyName in scope.collection[i]) {
									if (propertyName != scope.idField) {
										scope.mappedModel[propertyName] = scope.collection[i][propertyName];
									}
								}
								break;
							}
						}
					}
				});
			}
		};
	});
})();