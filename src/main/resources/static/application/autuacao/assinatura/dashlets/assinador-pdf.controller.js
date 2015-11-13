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
		
        uploader.onCompleteItem = function(fileItem, response) {
        	var documento = recuperarDocumentoPorItem(fileItem);
        	documento.documentoTemporario = response;
        	
        	preSign();
        };
		
        function preSign() {
        	AssinaturaService.requestUserCertificate().then(function(certificate) {
        		
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

