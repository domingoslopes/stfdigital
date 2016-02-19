package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando responsável por enviar os dados da autuação de processo recursal criminal eleitoral para o back-end.
 * 
 * @author Anderson.Araujo
 * 
 * @since 26.01.2016 
 *
 */
@ApiModel(value = "Contém as informações necessárias para autuar a petição enviada pelo peticionador")
public class AutuarProcessoCriminalEleitoralCommand {
	
	@NotNull
	@ApiModelProperty(value = "Id do processo recursal.", required=true)
	private Long processoId;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private List<String> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private List<String> partesPoloPassivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com os assuntos", required=true)
	private List<String> assuntos;
		
	public Long getProcessoId() {
		return processoId;
	}

	public List<String> getPartesPoloAtivo() {
		return partesPoloAtivo;
	}

	public List<String> getPartesPoloPassivo() {
		return partesPoloPassivo;
	}

	public List<String> getAssuntos() {
		return assuntos;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
}
