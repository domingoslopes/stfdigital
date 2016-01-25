/**
 * @author Lucas.Rodrigues
 * @since 1.0.0
 */
(function() {
	'use strict';
	
	/**
	 * @ngdoc directive
	 * @name wodotexteditor
	 * @memberOf plataforma
	 * 
	 * @description Diretiva para renderizar um editor de texto odf.
	 * 
	 * @example
	 * <div wodotexteditor="url_documento_odt"></div>
	 */
	angular.plataforma.directive('wodotexteditor', ['SecurityService', 'messages', 'properties', function(SecurityService, messages, properties) {
		return {
			restrict : 'EA',
			scope: false,
			link: function(scope, elem, attrs) {
				if(angular.isUndefined(attrs.id)) {
					messages.error("O id do elemento não foi definido!");
					return;
				}
				var instance = null;
				var isInitialized = false;
				var user = SecurityService.user();
				var editorOptions = {
					userData: {
						fullName: user.login,
						color: "black"
					},
					allFeaturesEnabled: true
	            };
				
				var openDocument = function(editor) {
					if (attrs.wodotexteditor != '') {
						
						editor.openDocumentFromUrl(scope[attrs.wodotexteditor], function(err) {
							if (err) {
								messages.error("Erro ao abrir documento: " + err);
							}
						});
					}
				};
				
				var onEditorCreated = function(err, editor) {
					if (err) {
						messages.error(err);
						return;
					}
					$('.webodfeditor-toolbarcontainer').css('position', 'relative');
					
					openDocument(editor);
					instance = editor;
				};
				
				scope.$watch(attrs.wodotexteditor, function() {
					if (scope[attrs.wodotexteditor] != '') {
						if (instance == null) {
							$(elem).width('900px').height('870px');
							Wodo.createTextEditor(attrs.id, editorOptions, onEditorCreated);
						} else {
							instance.closeDocument(function() {});
							openDocument(instance);
						}
					}
				});
				
		        scope.$on('$destroy', function() {
		        	if (instance != null) {
		        		dijit.registry.byId('wodotexteditor').destroyRecursive();
		        	}
		        });
				
			}
		};
	}]);
})();

