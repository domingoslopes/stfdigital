package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rafael Alencar
 * 
 * @since 17.02.2016
 */
@ApiModel(value = "Contém as informações necessárias para organizar as peças de um processo")
public class OrganizarPecasCommand {

	@NotNull
	@ApiModelProperty(value = "Id do processo que terá as peças organizadas.", required=true)
	private Long processoId;
	
	@NotNull
	@ApiModelProperty(value = "Lista de peças organizadas.", required=true)
	private List<Long> pecasOrganizadas;
	
	@NotNull
	@ApiModelProperty(value = "Indica se a tarefa será concluída ou não.", required=true)
	private Boolean concluirTarefa;

	public Long getProcessoId() {
		return processoId;
	}
	
	public List<Long> getPecasOrganizadas() {
		return pecasOrganizadas;
	}
	
	public Boolean isConcluirTarefa() {
		return concluirTarefa;
	}
	
}
