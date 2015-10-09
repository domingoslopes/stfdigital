/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function() {
	'use strict';
	
	angular.autuacao.directive('stfVisualizadorPdf', ['visualizadorPdfService', function(visualizador) {
		return {
			scope : {
				pdfFile: '=stfVisualizadorPdf'
			},
			controller: function() {
				
			},
			link: function(scope, element, attrs) {
				scope.$watch('pdfFile', function(pdfFile) {
					if (isString(pdfFile)) {
						visualizador.pdfFileUrl = pdfFile;
					} else {
						visualizador.pdfFile = pdfFile;
					}
				});
				function isString(s) {
				    return typeof(s) === 'string' || s instanceof String;
				}
			}
		};
	}]);
	
	directives.directive('stfVisualizadorPdfIframe', ['visualizadorPdfService', function(visualizador) {
		return {
			require : '^stfVisualizadorPdf',
			scope : {
			},
			template: '<iframe id="viewerFrame" data-ng-src="{{trustSrc(urlPdf)}}" class="viewerFrame" frameborder="0" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling="no"></iframe>',
			controller: ['$scope', '$sce', function($scope, $sce) {
				$scope.urlPdf = '';
				
				$scope.trustSrc = function(src) {
					return $sce.trustAsResourceUrl(src);
				};
			}],
			link : function(scope, element, attrs) {
				scope.$watch(function() {
					return visualizador.pdfFile;
				}, function(pdfFile) {
					if (pdfFile) {
						window.URL = window.webkitURL || window.URL;
						scope.urlPdf = window.URL.createObjectURL(pdfFile);
					}
				});
				scope.$watch(function() {
					return visualizador.pdfFileUrl;
				}, function(pdfFileUrl) {
					if (pdfFileUrl) {
						scope.urlPdf = pdfFileUrl;
					}
				});
			}
		};
	}]);
});