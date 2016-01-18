package br.jus.stf.processamentoinicial.distribuicao.interfaces.commands;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 13.01.2016
 */
@ApiModel(value = "Contém as informações necessárias para cadastrar processo recursal")
public class CadastrarRecursalCommand {
	@NotNull
	@ApiModelProperty(value = "Id do processo recursal.", required=true)
	private Long processoId;
	
	@NotNull
	@ApiModelProperty(value = "Id da classe processual.", required=true)
	private String classeId;
	
	@NotNull
	@ApiModelProperty(value = "Número do processo recursal.", required=true)
	private Long numero;
	
	@NotNull
	@ApiModelProperty(value = "Id da petição vinculada ao processo recursal.", required=true)
	private Long peticaoId;
	
	@NotNull
	@ApiModelProperty(value = "Situação do processo recursal.", required=true)
	private String situacao;
	
	@ApiModelProperty(value = "Lista de preferências do processo recursal.")
	private Set<Long> preferencias;

	public Long getProcessoId() {
		return processoId;
	}
	
	public String getClasseId() {
		return classeId;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public Long getPeticaoId() {
		return peticaoId; 
	}
	
	public String getSituacao() {
		return situacao; 
	}
	
	public Set<Long> getPreferencias() {
		return preferencias; 
	}
	
	public void setProcessoId(Long processoId) {
		this.processoId = processoId;
	}
	
	public void setClasseId(String classeId) {
		this.classeId = classeId;
	}
	
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public void setPeticaoId(Long peticaoId) {
		this.peticaoId = peticaoId;
	}
	
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public void setPreferencias(Set<Long> preferencias) {
		this.preferencias = preferencias;
	}
}
