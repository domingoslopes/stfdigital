/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	return;
	angular.autuacao.controller('AssinadorPdfDashletController', ['$scope', '$cookies', 'FileUploader', 'properties', 'SignatureService', function($scope, $cookies, FileUploader, properties, SignatureService) {
		
		$scope.documentos = [];
		
		var uploader = $scope.uploader = new FileUploader({
            url: properties.apiUrl + '/certification/signature/upload-to-sign',
            headers : {'X-XSRF-TOKEN': $cookies.get('XSRF-TOKEN')},
            formData: [{name: "file"}],
            filters: [{
		    	name: 'arquivos-pdf',
		    	fn: function (file) {
		    		if (file.type === 'application/pdf') {
			    		return true;
		    		} else {
		    			messages.error('Não foi possível anexar o arquivo "' + file.name + '". <br />Apenas documentos *.pdf são aceitos.');
		    			return false;
		    		}
		    	}
		    }, {
		    	name: 'tamanho-maximo',
		    	fn: function(file) {
		    		if (file.size / 1024 / 1024 > 100) {
		    			messages.error('Não foi possível anexar o arquivo "' + file.name + '". <br />O tamanho do arquivo excede 100mb.');
		    			return false;
		    		}
		    		return true;
		    	}
		    }]
        });
		
		var signingTracker = SignatureService.signerWithExternalUpload();
		
		uploader.onAfterAddingFile = function(fileItem) {
			var signer = signingTracker.newSigner();
			
            var documento = {
				"fileItem" : fileItem,
				"signedDocument" : null,
				"signer" : signer
            };
            $scope.documentos.push(documento);
                        
            signer.onSignerCreated(function(signerId) {
            	console.log('controller-signerId');
            	console.log(signerId);
            	fileItem.headers['Signer-Id'] = signerId;
            	fileItem.upload();
            });
            signer.onSigningCompleted(function(signedDocument) {
            	console.log(signedDocument);
            	documento.signedDocument = signedDocument;
            });
            signer.onErrorCallback(function(error) {
            	console.log('controller-error-callback');
            	console.log(error);
            });
            signer.start();
		};
		
        uploader.onCompleteItem = function(fileItem, response) {
        	var documento = recuperarDocumentoPorItem(fileItem);
        	documento.documentoTemporario = response;
        	
        	var signer = documento.signer;
        	
        	signer.triggerFileUploaded();
        };
        
        function recuperarDocumentoPorItem(item) {
			var d = null;
			angular.forEach($scope.documentos, function(documento) {
				if (documento.fileItem == item) {
					d = documento;
				}
			});
			return d;
		}
        
	}]);
	
})();

