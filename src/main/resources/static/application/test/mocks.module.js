/**
 * @author Lucas Mariano
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('mocks', ['ngMockE2E']).run(function($log, $httpBackend) {
		$log.debug('Aplicação carregada. Mocando as services...');
		
		var tarefas = [
  	        {id: '01', nome : 'preautuacao', descricao : 'Pré-autuar Processo'}, 
	        {id: '02', nome : 'autuacao', descricao : 'Autuar Processo'}, 
	        {id: '03', nome : 'distribuicao', descricao : 'Distribuir Processo'}
	    ]; 
		  
		$httpBackend.whenPOST('/api/peticao/fisica').respond(function(method, url, data, headers){
			console.log('Recebendo dados:', method, url, data, headers);
			tarefas.push(angular.fromJson({id: '04', nome : 'preautuacao', descricao : 'Pré-autuar Processo'}));
			return [200, {}, {}];
		});
		  
		$httpBackend.whenGET('/api/tarefas').respond(function(method,url,data) {
			console.log("Recuperando as tarefas");
			return [200, tarefas, {}];
		});
		
		$httpBackend.whenGET(/\/.*.tpl.html/).passThrough();
		
	});

})();

