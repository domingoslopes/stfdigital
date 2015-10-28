/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 21.08.2015
 */ 
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Autuação Controller', function() {
		var fakeData = [];
		var stateParams = [];
		var controller;
		var scope;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($controller, $httpBackend, $window, $log, properties) {
			$window.sessionStorage.papel = JSON.stringify('autuador');
			$httpBackend.expectGET(properties.apiUrl + '/classes').respond([{sigla : 'AP', nome: 'Ação Penal'}, {sigla : 'ADI', nome: 'Ação Direta de Inconstitucionalidade'}]);
			$httpBackend.expectGET(properties.apiUrl + '/peticoes/1').respond({classe : 'AP'});
			$httpBackend.whenGET(properties.apiUrl + '/workflow/tarefas').respond([{}]);

			stateParams = {idTarefa: 1};
			
			controller = $controller('AutuacaoController', {
				$stateParams : stateParams,
				$log: $log
			});

			$httpBackend.flush();
		}));

		it('Deveria carregar identificador da petição no escopo do controlador', function() {
			expect(controller.idPeticao).toEqual(1);
		});
		
		it('Deveria carregar a lista de classes no escopo do controlador', function() {
			expect(controller.classes.length).toEqual(2);
		});
		
		it('Deveria carregar a petição a autuar no escopo do controlador', function() {
			expect(controller.peticao.classe).toEqual('AP');
		});
		
	});
})();
