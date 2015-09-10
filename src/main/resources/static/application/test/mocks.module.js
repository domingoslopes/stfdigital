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
		
		var tarefas = [];
	        /*[{id: '04', nome: 'registro', descricao : 'Receber Processo Físico'},
	        {id: '05', nome : 'preautuacao', descricao : 'Pré-autuar Processo'}
	    ];*/ 
		
		var actions = [{
		    id: "dummy_action",
		    description: "Dummy Action",
		    resourcesType: "DummyObj",
		    hasConditionHandlers: true,
		    resourcesMode: "Many",
		    neededAuthorities: ["peticionador"]
		}, {
		    id: "do_nothing",
		    description: "Do Nothing",
		    resourcesType: "Long",
		    hasConditionHandlers: false,
		    resourcesMode: "None",
		    neededAuthorities: []
		}, {
		    id: "do_nothing_long",
		    description: "Do Nothing Long",
		    resourcesType: "Long",
		    hasConditionHandlers: false,
		    resourcesMode: "One",
		    neededAuthorities: ["peticionador"]
		}, {
		    id: "registrar_peticao",
		    description: "Registrar",
		    resourcesType: "RegistrarPeticaoCommand",
		    hasConditionHandlers: false,
		    resourcesMode: "One",
		    neededAuthorities: ["peticionador"]
		}];
		
		var classes = [{ sigla : "ADI", nome : "Ação Direta de Inconstitucionalidade" },
		               { sigla : "AP", nome : "Ação Penal" },
		               { sigla : "HC", nome : "Habeas Corpus" }];
		
		var tipoRecebimentos = [{ sigla : "1", nome : "Balcão" },
		               { sigla : "2", nome : "Sedex" },
		               { sigla : "3", nome : "Malote" },
		               { sigla : "4", nome : "Fax" },
		               { sigla : "5", nome : "Email" }];
		
		var peticaoEletronica = {id: '2', classe : 'AP', partes: {poloAtivo : ['joao'], poloPassivo : ['maria']}, detalhes: 'detalhes'};
		var peticoesEletronicas = [peticaoEletronica];
		
		var peticaoFisica = {id: '2',qtdVolumes: '2',  qtdApensos: '2', tipoRecebimento : '2', numSedex : '2'};
		var peticoesFisicas = [peticaoFisica];
		
		var ministros = [{id: 1, nome: 'Min. Roberto Barroso'}, {id: 2, nome: 'Min. Sicrano'}, {id: 3, nome: 'Min. João'}];
		  
		$httpBackend.whenPOST(properties.apiUrl + '/peticao').respond(function(method, url, data, headers){
			console.log('Enviando peticao eletronica:', method, url, data, headers);
			tarefas = [];
			tarefas.push({id: '2', nome : 'autuacao', descricao : 'Autuar Processo'});
			return [200, peticaoEletronica.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticao/fisica').respond(function(method, url, data, headers){
			console.log('Enviando peticao fisica:', method, url, data, headers);
			tarefas.push({id: '2', nome : 'preautuacao', descricao : 'Pré-autuar Processo'});
			return [200, peticaoFisica.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticao/2/autuacao').respond(function(method, url, data, headers){
			console.log('Autuando peticao:', method, url, data, headers);
			tarefas = [{id: '2', nome : 'distribuicao', descricao : 'Distribuir Processo'}];
			return [200, peticaoEletronica.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticao/2/distribuicao').respond(function(method, url, data, headers){
			console.log('Distribuindo peticao:', method, url, data, headers);
			tarefas = [];
			return [200, {classe: 'AP', numero: '2', relator: ministros[0].nome}, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticao/2/preautuacao').respond(function(method, url, data, headers){
			console.log('Preautuando peticao:', method, url, data, headers);
			tarefas = [{id: '2', nome : 'distribuicao', descricao : 'Distribuir Processo'}];
			return [200, peticaoFisica.id, {}];
		});
		
/*		$httpBackend.whenPOST(properties.apiUrl + '/peticao/fisica/02').respond(function(method, url, data, headers){
			console.log('Recebendo peticao fisica:', method, url, data, headers);
			tarefas = [{id: '02', nome : 'pre-atuacao', descricao : 'Pré-autuar Processo'}];
			return [200, peticaoFisica.id, {}];
		});
		*/
		$httpBackend.whenGET(properties.apiUrl + '/peticoes').respond(function(method, url, data, headers){
			console.log('Recebendo peticoes:', method, url, data, headers);
			return [200, peticoesEletronicas, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/classes').respond(function(method, url, data, headers){
			console.log('Recebendo classes:', method, url, data, headers);
			return [200, classes, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/ministros').respond(function(method, url, data, headers){
			console.log('Recebendo ministros:', method, url, data, headers);
			return [200, ministros, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/peticao/2').respond(function(method, url, data, headers){
			console.log('Recebendo peticao eletronica:', peticaoEletronica);
			return [200, peticaoEletronica, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/peticao/fisica/2').respond(function(method, url, data, headers){
			console.log('Recebendo peticao fisica:', peticaoFisica);
			return [200, peticaoFisica, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/tiporecebimentos').respond(function(method, url, data, headers){
			console.log('Tipo de recebimento da peticao:', method, url, data, headers);
			return [200, tipoRecebimentos, {}];
		});
		  
		$httpBackend.whenGET(properties.apiUrl + '/tarefas').respond(function(method,url,data, headers) {
			console.log("Recuperando as tarefas");
/*			if (headers.papel == 'recebedor') {
				taref
			}*/
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
			return [200, "Ação executada!", {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/actions/registrar_peticao/execute').respond(function(method,url,data) {
			console.log("Executando uma ação");
			return [200, "2345/2015", {}];
		});
		
		$httpBackend.whenGET(/\/.*.tpl.html/).passThrough();
		
	//	$httpBackend.whenGET(properties.apiUrl + '/classes').passThrough();
		
	});

})();

