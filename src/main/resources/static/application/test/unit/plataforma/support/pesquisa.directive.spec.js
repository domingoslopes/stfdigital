/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Directive: Pesquisa', function() {
		var scope;
		var element;
		var template;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function($compile, $rootScope, $httpBackend, properties, $window) {
			scope = $rootScope.$new();
			scope.configuracao = {
					texto : 'identificacao',
					indices : ['autuacao', 'distribuicao'],
					filtros : ['identificacao'],
					campos : ['identificacao', 'relator.codigo'],
					tipos : ['PeticaoEletronica', 'PeticaoFisica', 'Processo'],
					ordenadores: {'identificacao' : 'ASC'},
					pesquisa : 'sugestao'
				};
			element = angular.element('<pesquisa id="pesquisa" configuracao="configuracao" />');
			template = $compile(element)(scope);
			scope.$digest();
		}));

		it('Deveria apresentar um select para pesquisa', function() {
			expect(template[0].outerHTML).toMatch("select2-offscreen");
		});
	});
})();
