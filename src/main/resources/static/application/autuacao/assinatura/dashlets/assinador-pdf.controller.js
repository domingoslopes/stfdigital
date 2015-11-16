/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.autuacao.controller('AssinadorPdfDashletController', ['$scope', 'FileUploader', 'properties', 'AssinaturaService', function($scope, FileUploader, properties, AssinaturaService) {
		
		$scope.documentos = [];
		
		var uploader = $scope.uploader = new FileUploader({
            url: properties.apiUrl + '/certification/upload-to-sign',
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
		
		uploader.onAfterAddingFile = function(fileItem) {
            var documento = {
				fileItem : fileItem,
				documentoTemporario : null,
				tipo : null
            };
            $scope.documentos.push(documento);
			fileItem.upload();
		};
		
		var signingCertificate;
		
        uploader.onCompleteItem = function(fileItem, response) {
        	var documento = recuperarDocumentoPorItem(fileItem);
        	documento.documentoTemporario = response;
        	
        	sign(documento.documentoTemporario);
        };

        function sign(docId) {
        	
        }
        
        function PreAssinarCommand(tempDocId, certificate) {
        	this.tempDocId = tempDocId;
        	this.certificate = certificate;
        }
        
        function preSign(docId) {
        	AssinaturaService.requestUserCertificate().then(function(certificate) {
        		var command = new PreAssinarCommand(docId, certificate.hex)
        		console.log(command);
        		AssinaturaService.preSign(command).then(function(preSignatureDto) {
        			sign(certificate, preSignatureDto);
        		}, function(error) {
        			
        		});
        	}, function(error) {
        		
        	});
        }
        
        function sign(certificate, preSignatureDto) {
        	AssinaturaService.sign(certificate, preSignatureDto.hash, preSignatureDto.hashType).then(function(signature) {
        		AssinaturaService.sign(certificate, preSignatureDto.hash, preSignatureDto.hashType);
//        		AssinaturaService.postSign(preSignatureDto.signatureContextId, signature).then(function() {
//        			
//        		}, function(error) {
//        			
//        		});
        	}, function(error) {
        		
        	});
        }
        
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

