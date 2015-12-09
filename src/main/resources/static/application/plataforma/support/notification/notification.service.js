/**
 * @author Tomas.Godoi
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */
(function() {
	'use strict';
	
	angular.plataforma.service('NotificationService', ['$http', '$q', '$rootScope', '$filter', 'properties', 'PesquisaService', '$cookies', 'SecurityService', 
	                                                   function($http, $q, $rootScope, $filter, properties, PesquisaService, $cookies, SecurityService) {
		
		var socket = new SockJS(properties.apiUrl + '/ws/notificacoes');
		var client = Stomp.over(socket);
		var connected = false;
		var subscription = null;
		var callbackRegistrarNotificacao;
		
		client.heartbeat.outgoing = 25000;
		client.heartbeat.incoming = 25000;
		
		socket.onheartbeat = function() {
			$http.get(properties.apiUrl + '/management/info');
		};
		
		this.registrarNotificacao = function(cb) {
			callbackRegistrarNotificacao = cb;
			conectar();
		};
		
		this.listarLidas = function(notificacoes) {
			var command = new PesquisarCommand(true);
        	return PesquisaService.pesquisar(command).then(function(resultado) {
        		for(var i = notificacoes.length - 1; i >= 0; i--){
        	        if(notificacoes[i].lida === true){
        	        	notificacoes.splice(i,1);
        	        }
        	    }
        		aoReceberNotificacao(resultado);
        	});
		};
		
		this.listarNaoLidas = function() {
			var command = new PesquisarCommand(false);
        	return PesquisaService.pesquisar(command).then(aoReceberNotificacao);
		};
		
		this.marcarComoLida = function(notificacoes) {
			var lidas = [];
			angular.forEach(notificacoes, function(notificacao) {
				notificacao.lida = true;
				lidas.push(notificacao.id);
			});
			var command = { notificacoes : lidas };
			return $http.put(properties.apiUrl + '/notificacoes', command);
		};
		
		var conectar = function() {
			return $q(function(resolve, reject) {
				if (!connected) {
					var headers = connHeaders();
					client.connect(headers, function() {
						connected = true;
						ativarNotificacoes(headers);
						resolve();
					}, function() {
						reject('Erro ao conectar no serviço de notificação!');
					});
				} else {
					resolve();
				}
			});
		};
		
		var Notificacao = function(id, mensagem, hora, lida) {
			this.id = id;
			this.mensagem = mensagem;
			this.hora = hora;
			this.lida = lida;
		};
		
		var PesquisarCommand = function(lida) {
			var login = connHeaders().login;
			
	    	this.indices = ['notificacao']; 
	        this.tipos = ['Notificacao'];
	    	this.filtros = {'notificado' : [login], 'lida' : [lida]};
	        this.ordenadores = {'dataCriacao' : 'DESC'};
		};
		
		var ativarNotificacoes = function(headers) {
			if (connected) {
				subscription = client.subscribe('/user/' + headers.login + '/notificacoes', aoReceberNotificacao, headers);
			}
		};
		
		var pararNotificacoes = function() {
			if (connected && subscription !== null) {
				subscription.unsubscribe();
			}
		};
		
		var aoReceberNotificacao = function(result) {
			if (angular.isDefined(result.body)) {
				var dto = JSON.parse(result.body);
				carregarNotificacao(dto, dto.id);
				notificar(dto.mensagem);
				$rootScope.$apply();
				
			} else if (angular.isDefined(result.data)) {
				angular.forEach(result.data, function(dto) {
					carregarNotificacao(dto.objeto, dto.id);
				});
			}
		};
		
		var carregarNotificacao = function(not, id) {
			if (angular.isFunction(callbackRegistrarNotificacao)) {
				var notificacao = new Notificacao(id, not.mensagem, not.dataCriacao, not.lida);
				callbackRegistrarNotificacao(notificacao);
			} else {
				throw "Uma função de callback deve ser registrada para carregar as notificações!";
			}
		};
		
		var connHeaders = function() {
			var headers = {};
			headers['X-XSRF-TOKEN'] = $cookies.get('XSRF-TOKEN');
			
			var user =  SecurityService.user();
			
			if (user) {
				headers['login'] = user.login();
			};
			
			return headers;
		};
		
		var notificar = function(message) {
			$('body').pgNotification({
                style: 'flip',
                message: message,
                position: "top-right",
                timeout: 6000,
                type: "info"
            }).show();
		};
	}]);
})();
