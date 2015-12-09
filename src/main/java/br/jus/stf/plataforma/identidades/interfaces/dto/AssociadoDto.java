package br.jus.stf.plataforma.identidades.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para transportar os dados de um associado do back-end para o front-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 27.11.2015
 *
 */
@ApiModel(value="Objeto usado para transportar os dados de um associado do back-end para o front-end.")
public class AssociadoDto {
	
	@ApiModelProperty(value = "Id do órgão")
	private Long idOrgao;
	
	@ApiModelProperty(value = "Número do CPF do associado.")
	private String cpf;
	
	@ApiModelProperty(value = "Nome do associado.")
	private String nome;
	
	@ApiModelProperty(value = "Tipo de associação.")
	private String tipoAssociacao;
	
	@ApiModelProperty(value ="Cargo do associado.")
	private String cargo;

	public AssociadoDto(Long idOrgao, String cpf, String nome, String tipoAssociacao, String cargo){
		this.idOrgao = idOrgao;
		this.cpf = cpf;
		this.nome = nome;
		this.tipoAssociacao = tipoAssociacao;
		this.cargo = cargo;
	}
	
	public Long getIdOrgao() {
		return this.idOrgao;
	}

	public void setIdOrgao(Long id) {
		this.idOrgao = id;
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
