/**
 * Controlador da tela de permissões
 * 
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 11.12.2015
 */
(function($) {
	angular.plataforma.controller('PermissoesController', function($scope) {
		// Passo de preenchimento da tela
		this.passo = 1;
		
		// Configuração da pesquisa de usuário
		this.configuracaoPesquisaUsuario = {
			texto : 'nome',
			indices : ['pessoa'],
			filtros : ['nome'],
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
		
		// Observa mudança do usuário selecionado
		$scope.$watch('vm.usuario', function(novoValor) {
			this.selecionarUsuario(this.usuario.objeto);
		});
		
		// Recupera os usuários
		//this.usuarios
		
		// Callback de seleção de um usuário
		this.selecionarUsuario = function(usuario) {
			// Se o usuário for nulo, volta ao primeiro passo 
			if (usuario === null) {
				this.passo = 0;
				return;
			}
			
			// Próximo passo
			this.passo = 2;
			this.detalhes.idUsuario = usuario.id.sequencial;
			
			// Selecionar todos os papeis
			// Selecionar papeis do usuário
			// Selecionar todos os grupos
			// Selecionar grupos do usuário
		};
		
	});
})(jQuery);