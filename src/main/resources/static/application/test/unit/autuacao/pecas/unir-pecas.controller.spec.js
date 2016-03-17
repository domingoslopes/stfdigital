/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0M6
 * @since 12.02.2016
 */ 
/* jshint undef:false*/
(function() {
	'use strict';
	
	var mockResource1 = {
			peca: {id: 1, tipoPecaId : 2, visibilidade: 'P', situacao: 'JUNTADA', descricao: 'peca juntada do processo RE 7', documentoId: 1},
			processoId : 3
	};
	var mockResource2 = {
			peca: {id: 2, tipoPecaId : 1, visibilidade: 'P', situacao: 'JUNTADA', descricao: 'peca juntada do processo RE 8', documentoId: 2},
			processoId : 3
	};
	
	var primeiroDocumento = {
			peca: {documentoId: 1, tamanho : 2000, quantidadePaginas: 'P'}
	};
	var segundoDocumento = {
			peca: {documentoId: 1, tamanho : 5000, quantidadePaginas: 'P'}
	};
	
	describe('Unir peças Controller', function() {
		var controller;
		var $httpBackend;
		var properties;
		var scope;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, messages, _properties_, PecaService) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			$httpBackend.expectGET(properties.apiUrl + '/documentos/1').respond(primeiroDocumento);
			$httpBackend.expectGET(properties.apiUrl + '/documentos/2').respond(segundoDocumento);
			
			
			scope = $rootScope.$new();
			scope.modal = {close: function(){}};
			
			controller = $controller('UnePecasController', {
				$scope: scope,
				$stateParams: {resources: [mockResource1, mockResource2]},
				messages: messages, 
				PecaService: PecaService
			});
			$httpBackend.flush();
		}));

		it('Deveria inicializar o controlador', function() {
			expect(controller).not.toBeNull();
		});
		
		it('Deveria validar se a peça possui mais de 10Mb', function() {
			if (scope.tamanhoTotalPeca > 1000000){
				expect(scope.validar()).toBeFalsy();				
			}else{
				expect(scope.validar()).toBeTruthy();
			}
		});
		
	});
})();
