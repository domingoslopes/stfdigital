/**
 * angular-onlyoffie https://github.com/legalthings/angular-onlyoffice Copyright (c) 2015 ; Licensed
 * MIT
 */
angular.module('onlyoffice', []);

angular.module('onlyoffice').directive('onlyofficeEditor', [ function() {
	function key(k) {
		var result = k.replace(new RegExp("[^0-9-.a-zA-Z_=]", "g"), "_");
		return result.substring(result.length - Math.min(result.length, 50));
	}

	var getDocumentType = function(ext) {
		if (".docx.doc.odt.rtf.txt.html.htm.mht.pdf.djvu.fb2.epub.xps".indexOf(ext) != -1)
			return "text";
		if (".xls.xlsx.ods.csv".indexOf(ext) != -1)
			return "spreadsheet";
		if (".pps.ppsx.ppt.pptx.odp".indexOf(ext) != -1)
			return "presentation";
		return null;
	};

	return {
		restrict: 'AE',
		template : '<div id="onlyoffice-editor"></div>',
		scope : {
			save : '&',
			editorConfig: '=onlyofficeEditor'
		},
		link : function($scope, $element, $attrs) {
			$scope.$watch('editorConfig.document.src',
			function() {
				if (!$scope.editorConfig || !$scope.editorConfig.document || !$scope.editorConfig.document.src)
					return;
				var docUrl = $scope.editorConfig.document.src;

				var docTitle = $scope.editorConfig.document.name || docUrl;
				var docKey = key(docUrl);

				var docType = docUrl.split('?')[0].substring(docUrl.lastIndexOf(".") + 1).trim().toLowerCase();
				var documentType = getDocumentType(docType);
				
				var callbackUrl = $scope.editorConfig.document.callbackUrl;
				
				var defaultConfig = {
					type : "desktop",
					width : '100%',
					height : '100%',
					documentType : documentType,
					document : {
						title : docTitle,
						url : docUrl,
						fileType : docType,
						key : docKey,
						permissions : {
							edit : true,
							download : false
						}
					},
					editorConfig : {
						mode : 'edit',
						callbackUrl: callbackUrl
					},
					events : {
						onReady : function() {
							setTimeout(function() {
								$scope.$apply(function() {
									$scope.ready = true;
								});
							}, 5000);
						},
						onSave : function(event) {
							var url = event.data;
							$scope.save({
								url : url,
								close : $scope.close
							});
						}
					}
				};

				var config = angular.merge(defaultConfig, $scope.editorConfig);

				// creating object editing
				new DocsAPI.DocEditor("onlyoffice-editor", config);
			});
		},
		controller : [ '$scope', function($scope) {
			$scope.saveClose = function() {
				$scope.close = true;
				var window = angular.element('onlyoffice-editor iframe')[0].contentWindow;
				window.postMessage({
					command : 'save'
				}, '*');
			};

			$scope.$on('onlyofficeSaveClose', $scope.saveClose);
		} ]
	}
} ]);