/**
 * @author Lucas Mariano
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 10.07.2015
 */ 
(function() {
	'use strict';
	
	angular.module('mocks', ['ngMockE2E']).run(function($log, $httpBackend, properties) {
		$log.debug('Aplicação carregada. Mocando as services...');
		
		var tarefas = [
  	        {id: '01', nome : 'preautuacao', descricao : 'Pré-autuar Processo'}, 
	        {id: '02', nome : 'autuacao', descricao : 'Autuar Processo'}, 
	        {id: '03', nome : 'distribuicao', descricao : 'Distribuir Processo'}
	    ]; 
		
		var actions = [{
		    id: "dummy_action",
		    description: "Dummy Action",
		    resourcesType: "DummyObj",
		    hasConditionHandlers: true,
		    resourcesMode: "Many",
		    neededAuthorities: ["peticionador"],
		    context: "autuacao"
		}, {
		    id: "do_nothing",
		    description: "Do Nothing",
		    resourcesType: "Long",
		    hasConditionHandlers: false,
		    resourcesMode: "None",
		    neededAuthorities: [],
		    context: "plataforma"
		}, {
		    id: "do_nothing_long",
		    description: "Do Nothing Long",
		    resourcesType: "Long",
		    hasConditionHandlers: false,
		    resourcesMode: "One",
		    neededAuthorities: ["peticionador"],
		    context: "autuacao"
		}];
		
		var classes = [{ sigla : "ADI", nome : "Ação Direta de Inconstitucionalidade" },
		               { sigla : "HC", nome : "Habeas Corpus" }];
		  
		$httpBackend.whenPOST(properties.apiUrl + '/peticao').respond(function(method, url, data, headers){
			console.log('Enviando peticao:', method, url, data, headers);
			tarefas.push(angular.fromJson({id: '04', nome : 'preautuacao', descricao : 'Pré-autuar Processo'}));
			return [200, {}, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/peticoes').respond(function(method, url, data, headers){
			console.log('Recebendo peticoes:', method, url, data, headers);
			return [200, [], {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/classes').respond(function(method, url, data, headers){
			console.log('Recebendo classes:', method, url, data, headers);
			return [200, classes, {}];
		});
		  
		$httpBackend.whenGET(properties.apiUrl + '/tarefas').respond(function(method,url,data) {
			console.log("Recuperando as tarefas");
			return [200, tarefas, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/actions').respond(function(method,url,data) {
			console.log("Recuperando as ações");
			return [200, actions, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/actions/isallowed').respond(function(method,url,data) {
			console.log("Recuperando as ações verificadas");
			var actions = ["dummy_action"];
			return [200, actions, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/actions/dummy_action/isallowed').respond(function(method,url,data) {
			console.log("Verificando uma ação");
			return [200, true, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/actions/dummy_action/execute').respond(function(method,url,data) {
			console.log("Executando uma ação");
			return [200, null, {}];
		});
		
		$httpBackend.whenGET(/\/.*.tpl.html/).passThrough();
		
	});

})();

