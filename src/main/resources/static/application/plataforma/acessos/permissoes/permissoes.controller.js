/**
 * Controlador da tela de permissões
 * 
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 11.12.2015
 */
(function($) {
	angular.plataforma.controller('PermissoesController', function($scope, $state, messages, SecurityService, AcessosService) {
		// Erros
		this.possuiErrosGrupos = false;
		this.possuiErrosPapeis = false;
		
		// Passo de preenchimento da tela
		this.passo = 1;
		
		// Seleciona o setor do usuário logado
		this.proprioSetor = SecurityService.user().setorLotacao.codigo;

		// Configuração da pesquisa de usuário
		this.configuracaoPesquisaUsuario = {
			texto : 'pessoa.nome',
			indices : ['usuario'],
			filtros : ['pessoa.nome'],
			filtrosFixos: {'setor.id': [this.proprioSetor] },
			pesquisa : 'sugestao'
		};
		
		
		// Informações da tela
		this.detalhes = {
			idUsuario: 0,
			papeisAdicionados: [],
			papeisRemovidos: [],
			gruposAdicionados: [],
			gruposRemovidos: []
		};
		
		this.informacoesDeTela = {
			filtroUsuariosSetor: true
		}
		
		this.gruposUsuario = [];
		this.gruposRestantes = [];
		this.papeisUsuario = [];
		this.papeisRestantes = [];
		
		// Observa mudança do usuário selecionado
		$scope.$watch('vm.usuario', function(novoValor) {
			this.selecionarUsuario(novoValor ? novoValor.objeto : null);
		}.bind(this));
		
		// Observa mudança no filtro de usuários do setor
		$scope.$watch('vm.informacoesDeTela.filtroUsuariosSetor', function(novoValor) {
			this.filtrarUsuarios(novoValor)
		}.bind(this));
		
		// Seleciona todos os papeis
		AcessosService.papeis().then(function(response) {
			this.todosPapeis = response.data;
		}.bind(this));
		
		// Seleciona todos os grupos
		AcessosService.grupos().then(function(response) {
			this.todosGrupos = response.data;
		}.bind(this));
		
		/**
		 * Callback de seleção de um usuário. É chamado quando o campo que exibe
		 * os usuários é alterado.
		 * 
		 * @param usuario Objeto com as informações do usuário selecionado. 
		 * 				  Pode ser null, se o usuário que já estava selecionado foi removido.
		 */
		this.selecionarUsuario = function(usuario) {
			// Se o usuário for nulo, volta ao primeiro passo 
			if (usuario === null) {
				this.passo = 1;
				return;
			}
			
			// Próximo passo
			this.passo = 2;
			AcessosService.recuperarId(usuario.login).then(function(response) {
				this.detalhes.idUsuario = response.data;
			}.bind(this));
			
			// Selecionar papeis do usuário
			AcessosService.grupos(usuario.login).then(function(response){
				this.gruposUsuario = response.data;
				this.gruposRestantes = angular.copy(this.todosGrupos);
				
				this.gruposUsuario.forEach(function(grupo) {
					var index = -1;
					
					this.gruposRestantes.forEach(function(_grupo, _index) {
						if (grupo.id == _grupo.id) {
							index = _index;
							return false;
						}
						
						return true;
					})
					
					if (index > -1) {
						this.gruposRestantes.splice(index, 1);
					}
				}, this);
			}.bind(this), function() {
				this.possuiErrosGrupos = true;
			}.bind(this));
			
			// Selecionar grupos do usuário
			AcessosService.papeis(usuario.login).then(function(response){
				this.papeisUsuario = response.data;
				this.papeisRestantes = angular.copy(this.todosPapeis);

				this.papeisUsuario.forEach(function(papel) {
					var index = -1;
					
					this.papeisRestantes.forEach(function(_papel, _index) {
						if (papel.id == _papel.id) {
							index = _index;
							return false;
						}
						
						return true;
					})
					if (index > -1) {
						this.papeisRestantes.splice(index, 1);
					}
				}, this);
			}.bind(this), function() {
				this.possuiErrosPapeis = true;
			}.bind(this));
		};
		
		/**
		 * Callback para filtrar usuários. É chamado quando a seleção do checkbox
		 * é alterada.
		 * 
		 * @param selecionado Valor booleano indicando se o filtro está ativo ou não.
		 */
		this.filtrarUsuarios = function(selecionado) {
			if (selecionado) {
				this.configuracaoPesquisaUsuario.filtrosFixos['setor.id'] = [this.proprioSetor];
			} else {
				this.configuracaoPesquisaUsuario.filtrosFixos = [];
			}
		}
		
		/**
		 *  Formata o nome de um grupo
		 *  
		 *  @param Object grupo Entidade
		 *  @return String Nome do grupo
		 */
		this.formatarNomeGrupo = function(grupo) {
			return grupo.nome;
		}
		
		/**
		 *  Formata o nome de um papel
		 *  
		 *  @param Object papel Entidade
		 *  @return String Nome do papel
		 */
		this.formatarNomePapel = function(papel) {
			return papel.nome;
		}
		
		/**
		 * Callback de movimento de grupo para a direita (adiciona)
		 * 
		 * @param Array Grupos movidos para a direita
		 */
		this.gruposToRight = function(grupos) {
			grupos.forEach(function(grupo) {
				var index = this.detalhes.gruposRemovidos.indexOf(grupo.id);
				if (index > -1) {
					this.detalhes.gruposRemovidos.splice(index, 1);
				}
				
				if (this.detalhes.gruposAdicionados.indexOf(grupo.id) == -1) {
					this.detalhes.gruposAdicionados.push(grupo.id);
				}
			}, this);
		}
		
		/**
		 * Callback de movimento de papel para a esquerda (remove)
		 * 
		 * @param Array Papeis movidos para a esquerda
		 */
		this.papeisToLeft = function(grupos) {
			grupos.forEach(function(grupo) {
				var index = this.detalhes.gruposAdicionados.indexOf(grupo.id);
				if (index > -1) {
					this.detalhes.gruposAdicionados.splice(index, 1);
				}
				
				if (this.detalhes.gruposRemovidos.indexOf(grupo.id) == -1) {
					this.detalhes.gruposRemovidos.push(grupo.id);
				}
			}, this);
		}
		
		/**
		 * Callback de movimento de papel para a direita (adiciona)
		 * 
		 * @param Array Papeis movidos para a direita
		 */
		this.papeisToRight = function(papeis) {
			papeis.forEach(function(papel) {
				var index = this.detalhes.papeisRemovidos.indexOf(papel.id);
				if (index > -1) {
					this.detalhes.papeisRemovidos.splice(index, 1);
				}
				
				if (this.detalhes.papeisAdicionados.indexOf(papel.id) == -1) {
					this.detalhes.papeisAdicionados.push(papel.id);
				}
			}, this);
		}
		
		/**
		 * Callback de movimento de papel para a esquerda (remove)
		 * 
		 * @param Array Papeis movidos para a esquerda
		 */
		this.papeisToLeft = function(papeis) {
			papeis.forEach(function(papel) {
				var index = this.detalhes.papeisAdicionados.indexOf(papel.id);
				if (index > -1) {
					this.detalhes.papeisAdicionados.splice(index, 1);
				}
				
				if (this.detalhes.papeisRemovidos.indexOf(papel.id) == -1) {
					this.detalhes.papeisRemovidos.push(papel.id);
				}
			}, this);
		}
		
		/**
		 * 
		 */
		this.configurarPermissoes = function() {
			AcessosService.configurarPermissoes(this.detalhes).then(function() {
				$state.go('dashboard');
				messages.success("Permissões configuradas com sucesso.");
			});
		}
	});
})(jQuery);