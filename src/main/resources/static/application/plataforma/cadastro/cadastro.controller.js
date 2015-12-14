/**
 * Controlador da tela de cadastro
 * 
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 03.12.2015
 */
(function($) {
	angular.plataforma.controller('CadastroController', function($state, $timeout, CadastroService, CPFService) {
		
		this.detalhes = {
			login: "",
			nome: "",
			senha: "",
			senhaRepeticao: "",
			cpf: "",
			oab: "",
			email: "",
			telefone: "",
			tipoAdvogado: 0
		}
	
		$(document).ready(function () {
			// Identifica o formulário
			var form = $('#form-cadastro');

			// Adiciona o método de validação do CPF
			$.validator.addMethod('cpfValidator', function(value, element) {
				// Substitui pontos e traço
				var parsedValue = value.replace(/\.|-/g, '');
				
				// Se não terminou de digitar, não valida
				if (parsedValue.length != 11) {
					return true;
				}
				
				// Valida
				return CPFService.validarCPF(parsedValue);
			}.bind(this));
			
			// Ativa a validação do formulário
			form.validate({
				rules: {
					senha: {
						required: true,
	                    minlength: 5,
					},
					senhaRepeticao: {
                        equalTo: "[name=senha]"
					},
					cpf: {
						cpfValidator: true
					}
				},
				messages: {
					cpf: {
						cpfValidator: 'CPF Inválido'
					}
				}
			});
			
		}.bind(this));
		
		/**
		 * Realiza o cadastro de um usuário
		 */
		this.cadastrarUsuario = function cadastrarUsuario() {
			// Verifica validade do formulário
			if (this.detalhes.login.length == 0) {
				return false;
			}

			if (this.detalhes.nome.length == 0) {
				return false;
			}

			if (this.detalhes.senha.length < 5) {
				return false;
			}

			if (this.detalhes.senhaRepeticao != this.detalhes.senha) {
				return false;
			}

			if (this.detalhes.cpf.length > 0 && !CPFService.validarCPF(this.detalhes.cpf)) {
				return false;
			}
			
			// Cadastra o usuário
			CadastroService.cadastrar(this.detalhes)
				.then(function(response) {
					// Recebeu o ID do novo usuário
					if (response.id !== null) {
						this.exibirNotificacao("Usuário cadastrado com sucesso! Você será redirecionado em alguns segundos...", 'info');
						
						$timeout(function() {
							$state.go('login', {}, {reload: true});
						}, 5000);
					} else {
						this.exibirNotificacao("Ocorreu um erro. Por favor tente novamente mais tarde.", 'error');
					}
				}.bind(this), function(response) {
					this.exibirNotificacao("Ocorreu um erro. Por favor tente novamente mais tarde.", 'error');
				}.bind(this));
			
			return true;
		}
		
		/**
		 * Exibe uma notificação na tela
		 * 
		 * @param string mensagem
		 * @param string tipo 
		 */
		this.exibirNotificacao = function exibirNotificacao(mensagem, tipo) {
			$('body').pgNotification({
				message: mensagem,
				type: tipo,
				style: 'flip'
			}).show();
		}
		
	});
})(jQuery);