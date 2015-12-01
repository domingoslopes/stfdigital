package br.jus.stf.plataforma.identidades.interfaces.commands;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para transportar os dados de um associado do front-end para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.11.2015
 *
 */
@ApiModel(value="Objeto usado para transportar os dados de um associado do front-end para o back-end.")
public class AssociadoCommand {
	
	@ApiModelProperty(value = "Id do órgão", required=true)
	private Long idOrgao;
	
	@ApiModelProperty(value = "Número do CPF do associado.", required=true)
	private String cpf;
	
	@ApiModelProperty(value = "Nome do associado.", required=true)
	private String nome;
	
	@ApiModelProperty(value = "Tipo de associação.", required=true)
	private String tipoAssociacao;
	
	@ApiModelProperty(value ="Cargo do associado.", required=false)
	private String cargo;

	public Long getIdOrgao() {
		return this.idOrgao;
	}

	public void setIdOrgao(Long idOrgao) {
		this.idOrgao = idOrgao;
	}

	public String getCPF() {
		return this.cpf;
	}

	public void setCPF(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoAssociacao() {
		return this.tipoAssociacao;
	}

	public void setTipoAssociacao(String tipoAssociacao) {
		this.tipoAssociacao = tipoAssociacao;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}
