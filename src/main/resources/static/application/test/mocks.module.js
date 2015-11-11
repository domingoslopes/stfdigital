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
		
		var orgaos = [{ id : 1, nome : "AGU" }, { id : 2, nome : "PGR" }, { id : 3, nome : "União" }];
		
		var tipoRecebimentos = [{ id : 0, nome : "Balcão" },
		               { id : 1, nome : "Sedex" },
		               { id : 2, nome : "Malote" },
		               { id : 3, nome : "Fax" },
		               { id : 4, nome : "Email" }];
		
		var ministros = [{id: 0, nome: 'Min. Roberto Barroso'}, {id: 1, nome: 'Min. Sicrano'}, {id: 2, nome: 'Min. João'}, {id: 28, nome: 'Min. Celso de Mello'}];
		
		var tiposPeca = [{id : 1, descricao : "Petição Inicial"}, {id : 2 , descricao: "Ato coator"}, {id: 3, descricao: "Documentos Comprobatórios"}];
		
		//var tipoPecao - [{id : 0, nome : "Petição Inicial"}, {id : 2 , nome: "Ato coator"}, {id}]
		
		var peticao = {};
		
		var peticoes = [];
		
		var partes = {'Passivo' : [{id : 1, nome : 'Joao Fulano Silva'}], 'Ativo' : [{id: 2, nome: 'José da Silva Fulano'}, {id: 3, nome: 'Fulano da Silva'}]} 
			
		var dashletsPeticionador = ['minhas-tarefas', 'minhas-peticoes'];
		var dashletsRecebedor = ['minhas-tarefas', 'minhas-peticoes'];
		var dashletsRepresentante = ['minhas-tarefas', 'minhas-peticoes'];
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticoes').respond(function(method, url, data, headers){
			console.log('Enviando peticao eletronica:', method, url, data, headers);
			tarefas.push({id: '2', nome : 'autuacao', descricao : 'Autuar Processo'});
			//recebe os dados digitados na tela pelo peticionador.
			data = JSON.parse(data);
			
			peticao = {id: '2', classe: data.classe, partes: {poloAtivo : [data.partesPoloAtivo[0]], poloPassivo : [data.partesPoloPassivo[0]]}, detalhes: 'detalhes', qtdVolumes: '', 
					qtdApensos: '', numSedex : '', tipoRecebimento: '', ministro: ''};
			peticoes.push(peticao);
			return [200, peticao.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticoes/2/autuar').respond(function(method, url, data, headers){
			console.log('Autuando peticao:', method, url, data, headers);
			tarefas = [];
			data = JSON.parse(data);
			//sobrescreve a informa pois o autuador pode escolhe uma outra classe diferente da selecionada pelo peticionador.
			peticao.classe = data.classe;
			limparListaPeticoes(peticao);
			tarefas.push({id: '2', nome : 'distribuicao', descricao : 'Distribuir Processo'});
			return [200, peticao.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticoes/fisicas').respond(function(method, url, data, headers){
			console.log('Enviando peticao fisica:', method, url, data, headers);
			tarefas = [];
			tarefas.push({id: '2', nome : 'preautuacao', descricao : 'Pré-autuar Processo'});
			
			data = JSON.parse(data);
			var indiceTipoRecebimento = parseInt(data.tipoRecebimento);
			peticao.tipoRecebimento = tipoRecebimentos[indiceTipoRecebimento].nome;
			limparListaPeticoes(peticao);
			peticao = {id: '2', classe : '', partes: {poloAtivo : [], poloPassivo : []}, detalhes: 'detalhes', qtdVolumes: data.qtdVolumes, 
					qtdApensos: data.qtdApensos, numSedex : data.numSedex, tipoRecebimento: peticao.tipoRecebimento};
			return [200, peticao.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticoes/2/preautuar').respond(function(method, url, data, headers){
			
			console.log('Preautuando peticao:', method, url, data, headers);
			tarefas = [];
			tarefas.push({id: '2', nome : 'autuacao', descricao : 'Autuar Processo'});
			data = JSON.parse(data);
			//o preautuador pode alterar a classe da peticção "por enquanto";
			peticao.classe = data;
			limparListaPeticoes(peticao);
			return [200, peticao.id, {}];
		});
		
		$httpBackend.whenPOST(properties.apiUrl + '/peticoes/2/distribuir').respond(function(method, url, data, headers){
			console.log('Distribuindo peticao:', method, url, data, headers);
			tarefas = [];
			data = JSON.parse(data);
			var indice = parseInt(data);
			peticao.ministro = ministros[indice].nome;
			limparListaPeticoes(peticao);
			return [200, {classe: peticao.classe, numero: peticao.id, relator: peticao.ministro}, {}];
		});
		
		
		var commandPeticoes = {indices: ['autuacao'], campos: ['identificacao', 'dataCadastramento'], ordenadores: {'peticao.sequencial' : 'ASC'}, filtros: ['peticionador']};
		$httpBackend.whenPOST(properties.apiUrl + '/pesquisas', commandPeticoes).respond(function(method, url, data, headers){
			console.log('Pesquisa de peticao:', method, url, data, headers);
			var peticoesPesquisa = [];
			var peticao1 = {id: 1, objeto: {identificacao: '01/2015', dataCadastramento: '01-01-2011'}, processo: 'ADI/01'};
			var peticao2 = {id: 2, objeto: {identificacao: '02/2015', dataCadastramento: '02-02-2012'}, processo: 'HC/1000'};
			peticoesPesquisa.push(peticao1);
			peticoesPesquisa.push(peticao2);
			return [200, peticoesPesquisa, {}];
		});
		
		
		var commandPartes = {indices: ['pessoa'], campos: ['id.sequencial', 'nome'], ordenadores: {'nome' : 'ASC'}, filtros = { 'id.sequencial': [1,2] }};
		$httpBackend.whenPOST(properties.apiUrl + '/pesquisas', commandPartes).respond(function(method, url, data, headers){
			console.log('Pesquisa de partes:', method, url, data, headers);
			var partesPesquisa = [];
			var parte1 = {id: 1, objeto: {nome: 'Lucas Mariano'}};
			var parte2 = {id: 2, objeto: {nome: 'Rodrigo Barreiros'}};
			partesPesquisa.push(parte1);
			partesPesquisa.push(parte2);
			return [200, partesPesquisa, {}];
		});
		
		var commandPartesProcessos = {indices: ['distribuicao'], campos: ['processo.id.sequencial', 'processo.identificacao'], 
			ordenadores: {'peticao.sequencial' : 'ASC'}, filtros: {'processo.partes.sequencial' : 1}};
		$httpBackend.whenPOST(properties.apiUrl + '/pesquisas', commandPartesProcessos).respond(function(method, url, data, headers){
			console.log('Pesquisa dos processos da parte:', method, url, data, headers);
			var processosDaParte = [];
			var processo1 = {id: 1, objeto: {identificacao: 'ADI/01'};
			var processo2 = {id: 2, objeto: {identificacao: 'HC/1000'};
			processosDaParte.push(processo1);
			processosDaParte.push(processo2);
			return [200, processosDaParte, {}];
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
		
//------------------------------------------------- WHENGET's -------------------------------------------------		
		
		$httpBackend.whenGET(properties.apiUrl + '/peticoes').respond(function(method, url, data, headers){
			console.log('Recebendo peticoes:', method, url, data, headers);
			angular.forEach(peticoes, function(peticao) {
				peticao.isEletronico = true;
			});
			return [200, peticoes, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/classes').respond(function(method, url, data, headers){
			console.log('Recebendo classes:', method, url, data, headers);
			return [200, classes, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/orgaos').respond(function(method, url, data, headers){
			console.log('Recebendo orgãos:', method, url, data, headers);
			return [200, orgaos, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/ministros').respond(function(method, url, data, headers){
			console.log('Recebendo ministros:', method, url, data, headers);
			return [200, ministros, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/2/partes').respond(function(method, url, data, headers){
			console.log('Recebendo lista de partes da petição:', method, url, data, headers);
			return [200, partes, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/peticoes/2').respond(function(method, url, data, headers){
			console.log('Recebendo peticao:', peticao);
			return [200, peticao, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/tiporecebimentos').respond(function(method, url, data, headers){
			console.log('Tipo de recebimento da peticao:', method, url, data, headers);
			return [200, tipoRecebimentos, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/dashboards/padrao', {papel: 'peticionador'}).respond(function(method, url, data, headers){
			console.log('Recuperando as dashlets do papel selecionado:', method, url, data, headers);
			return [200, dashletsPeticionador, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/dashboards/padrao', {papel: 'recebedor'}).respond(function(method, url, data, headers){
			console.log('Recuperando as dashlets do papel selecionado:', method, url, data, headers);
			return [200, dashletsRecebedor, {}];
		});
		
		$httpBackend.whenGET(properties.apiUrl + '/dashboards/padrao', {papel:'representante'}).respond(function(method, url, data, headers){
			console.log('Recuperando as dashlets do papel selecionado:', method, url, data, headers);
			return [200, dashletsRepresentante, {}];
		});
		  
		$httpBackend.whenGET(properties.apiUrl + '/workflow/tarefas').respond(function(method,url,data, headers) {
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
		

		var limparListaPeticoes = function(peticao){
			peticoes = [];
			peticoes.push(peticao);
		};
		
	});

})();

