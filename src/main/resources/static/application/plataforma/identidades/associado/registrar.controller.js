/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.08.2015
 */ 
(function($) {
	'use strict';
	
	angular.plataforma.controller('RegistrarAssociadoController', function ($scope, $state, messages, AssociadoService, CPFService, SecurityService, OrgaoService) {
		if (SecurityService.hasPapel('gestor-cadastro')) {
			this.orgaosPromise = OrgaoService.listar();
		} else {
			this.orgaosPromise = OrgaoService.listarRepresentados();
		}
		
		this.orgaosPromise.then(function(response) {
			this.orgaos = response.data;
		}.bind(this));
		
		this.detalhes = {
				idOrgao: null,
				cpf: "",
				nome: "",
				tipoAssociacao: "Gestor",
				cargo: ""
		};
		
		$(document).ready(function () {
			// Identifica o formulário
			var form = $('#cadastro-associado');

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
		
		this.cadastrar = function cadastrar() {
			var idOrgao = parseInt(this.detalhes.idOrgao, 10);
			
			if ((idOrgao === NaN) || (idOrgao < 1)) {
				return false;
			}
			
			if (this.detalhes.cpf.length === 0 || !CPFService.validarCPF(this.detalhes.cpf)) {
				return false;
			}
			
			if (this.detalhes.nome.length === 0) {
				return false;
			}
			
			if (!AssociadoService.isTipoAssociacaoValido(this.detalhes.tipoAssociacao)) {
				return false;
			}

			AssociadoService.cadastrar(this.detalhes).then(function() {
				messages.success("Associado cadastrado com sucesso.");
				$state.go('dashboard');
			});
			
			return true;
		}
	});	
})(jQuery);