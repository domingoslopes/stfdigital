package br.jus.stf.plataforma.acessos.interfaces.commands;

import java.util.Set;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe usada para transportar os dados de configuração de permissões de usuário do front-end para o o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 03.12.2015
 *
 */
@ApiModel("Classe usada para transportar os dados de configuração de permissões de usuário do front-end para o o back-end.")
public class ConfigurarPermissoesUsuarioCommand {
	
	@ApiModelProperty(value = "Id do usuário.")
	private Long idUsuario;
	
	@ApiModelProperty(value = "Papéis do usuário.")
	private Set<Long> papeisAdicionados;
	
	@ApiModelProperty(value = "Papéis do usuário.")
	private Set<Long> gruposAdicionados;
	
	@ApiModelProperty(value = "Papéis do usuário.")
	private Set<Long> papeisRemovidos;
	
	@ApiModelProperty(value = "Papéis do usuário.")
	private Set<Long> gruposRemovidos;

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Set<Long> getPapeisAdicionados() {
		return this.papeisAdicionados;
	}

	public void setPapeisAdicionados(Set<Long> papeisAdicionados) {
		this.papeisAdicionados = papeisAdicionados;
	}

	public Set<Long> getGruposAdicionados() {
		return this.gruposAdicionados;
	}

	public void setGruposAdicionados(Set<Long> gruposAdicionados) {
		this.gruposAdicionados = gruposAdicionados;
	}
	
	public Set<Long> getPapeisRemovidos() {
		return this.papeisRemovidos;
	}

	public void setPapeisRemovidos(Set<Long> papeisRemovidos) {
		this.papeisRemovidos = papeisRemovidos;
	}

	public Set<Long> getGruposRemovidos() {
		return this.gruposRemovidos;
	}

	public void setGruposRemovidos(Set<Long> gruposRemovidos) {
		this.gruposRemovidos = gruposRemovidos;
	}
}
