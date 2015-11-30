/**
 * 
 */
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

	@ApiModelProperty(value="Nome do usuário.")
	private String nome;
	
	@ApiModelProperty(value="Setor de lotação do usuário.")
	private String setorLotacao;
	
	@ApiModelProperty(value="Lista de papéis associados ao usuário.")
	private Set<PapelDto> papeis;
	
	@ApiModelProperty(value="Lista de permissões do usuário.")
	private Set<GrantedAuthority> authorities;
	
	public UsuarioDto(String nome, String setorLotacao, Set<PapelDto> papeis, Set<GrantedAuthority> authorities) {
		this.nome = nome;
		this.setorLotacao = setorLotacao;
		this.papeis = papeis;
		this.authorities = authorities;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetorLotacao() {
		return this.setorLotacao;
	}

	public void setSetorLotacao(String setorLotacao) {
		this.setorLotacao = setorLotacao;
	}

	public Set<PapelDto> getPapeis() {
		return this.papeis;
	}

	public void setPapeis(Set<PapelDto> papeis) {
		this.papeis = papeis;
	}
}
