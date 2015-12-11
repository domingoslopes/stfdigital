package br.jus.stf.plataforma.acessos.interfaces.commands;

import org.hibernate.validator.constraints.NotBlank;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 03.12.2015
 */
@ApiModel(value = "Contem as informações necessárias para cadastrar um usuário")
public class CadastrarUsuarioCommand {
	@ApiModelProperty(value = "Login de acesso", required = true)
	@NotBlank
	private String login;

	@ApiModelProperty(value = "Nome da pessoa", required = true)
	@NotBlank
	private String nome;

	@ApiModelProperty(value = "Email", required = true)
	private String email;

	@ApiModelProperty(value = "CPF da pessoa", required = true)
	private String cpf;

	@ApiModelProperty(value = "Número da OAB")
	private String oab;

	@ApiModelProperty(value = "Telefone", required = true)
	private String telefone;

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the oab
	 */
	public String getOab() {
		return oab;
	}

	/**
	 * @param oab
	 *            the oab to set
	 */
	public void setOab(String oab) {
		this.oab = oab;
	}

	/**
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone
	 *            the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
