/**
 * Testes unitários do service messages.
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */
/* jshint undef:false*/
(function() {
	'use strict';

	describe('Service: messages', function() {
		var messages;

		beforeEach(module('appDev'));
		
		beforeEach(inject(function(_messages_) {
			messages = _messages_;
		}));
		
		it('Deveria ter construído mensagem de erro para um erro.', function() {
			var errorMessageDto = {"errors":[{"name":"file","type":"field","message":"Erro de teste."}]};
			var message = messages.buildErrorMessage(errorMessageDto);
			expect(message).toEqual("Erro de teste.");
		});
		
		it('Deveria ter construído mensagem de erro para um três erros.', function() {
			var errorMessageDto = {"errors":[
				{"name":"file","type":"field","message":"Erro de teste 1."},
				{"name":"file","type":"field","message":"Erro de teste 2."},
				{"name":"file","type":"field","message":"Erro de teste 3."}
			]};
			var message = messages.buildErrorMessage(errorMessageDto);
			expect(message).toEqual("Erro de teste 1. Erro de teste 2. Erro de teste 3.");
		});
	});
})();
