package br.jus.stf.plataforma.acessos.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe responsável por transportar os dados de usuário do back-end para o front-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.11.2015
 *
 */
@ApiModel(value = "Classe responsável por transportar os dados de usuário do back-end para o front-end.")
public class UsuarioDto {

	@ApiModelProperty("Id do usuário.")
	private Long id;
	
	@ApiModelProperty("Login do usuário.")
	private String login;
	
	@ApiModelProperty("Nome do usuário.")
	private String nome;
	
	@ApiModelProperty("Identificador da pessoa.")
	private Long pessoaId;
	
	@ApiModelProperty("Setor de lotação do usuário.")
	private SetorDto setorLotacao;
	
	public UsuarioDto() {
		
	}
	
	public UsuarioDto(Long id, String login, String nome, Long pessoaId, SetorDto setorLotacao) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.pessoaId = pessoaId;
		this.setorLotacao = setorLotacao;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Long getPessoaId() {
		return this.pessoaId;
	}

	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}

	public SetorDto getSetorLotacao() {
		return this.setorLotacao;
	}

	public void setSetorLotacao(SetorDto setorLotacao) {
		this.setorLotacao = setorLotacao;
	}
	
}
