package br.jus.stf.plataforma.acessos.interfaces.dto;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

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

	@ApiModelProperty(value="Id do usuário.")
	private Long id;
	
	@ApiModelProperty(value="Login do usuário.")
	private String login;
	
	@ApiModelProperty(value="Nome do usuário.")
	private String nome;
	
	@ApiModelProperty(value="Setor de lotação do usuário.")
	private SetorDto setorLotacao;
	
	@ApiModelProperty(value="Lista de papéis associados ao usuário.")
	private Set<PapelDto> papeis;
	
	@ApiModelProperty(value="Lista de permissões do usuário.")
	private Set<GrantedAuthority> authorities;
	
	public UsuarioDto(Long id, String login, String nome, SetorDto setorLotacao, Set<PapelDto> papeis, Set<GrantedAuthority> authorities) {
		this.id = id;
		this.login = login;
		this.nome = nome;
		this.setorLotacao = setorLotacao;
		this.papeis = papeis;
		this.authorities = authorities;
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

	public SetorDto getSetorLotacao() {
		return this.setorLotacao;
	}

	public void setSetorLotacao(SetorDto setorLotacao) {
		this.setorLotacao = setorLotacao;
	}

	public Set<PapelDto> getPapeis() {
		return this.papeis;
	}

	public void setPapeis(Set<PapelDto> papeis) {
		this.papeis = papeis;
	}

	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
}
