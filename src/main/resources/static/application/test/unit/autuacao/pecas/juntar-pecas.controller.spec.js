/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0M6
 * @since 12.02.2016
 */ 
/* jshint undef:false*/
(function() {
	'use strict';
	
	var mockResource = {
			peca: {id: 1, tipoPecaId : 2, visibilidade: 'P', situacao: 'JUNTADA', descricao: 'peca juntada do processo RE 7'},
			processoId : 3
		};
	
	
	describe('Juntar peças Controller', function() {
		var controller;
		var $httpBackend;
		var properties;
		
		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_$httpBackend_, $rootScope, $controller, $state, messages, _properties_) {
			$httpBackend = _$httpBackend_;
			properties = _properties_;
			
			var scope = $rootScope.$new();
			
			controller = $controller('JuntarPecasController', {
				$scope: scope,
				$stateParams: {resources: [mockResource]},
				messages: messages,
				properties: properties,
				$state: $state
			});
			
		}));

		it('Deveria inicializar o controlador', function() {
			expect(controller).not.toBeNull();
		});
		
		it('Deveria não validar a quando a peça estiver em uma situaça diferente de PEDENTE DE JUNTADA', function() {
			expect(controller.validar()).toBeFalsy();
		});
	});
})();
