package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@ApiModel(value = "Contém as informações necessárias para autuar a petição enviada pelo peticionador")
public class AutuarPeticaoCommand {

	@NotNull
	@ApiModelProperty(value = "Id da petição física registrada.", required=true)
	private Long peticaoId;
	
	@NotBlank
	@ApiModelProperty(value = "A classe processual definitiva, selecionada pelo autuador", required=true)
	private String classeId;
	
	@NotNull
	@ApiModelProperty(value = "Contém o resultado da análise do autuador, indicando se a petição é válida ou não", required=true)
	private Boolean valida;
	
	@ApiModelProperty(value = "Contém o motivo da recusa da petição, no caso de petições inválidas", required=true)
	private String motivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private List<String> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private List<String> partesPoloPassivo;
	
	public Long getPeticaoId() {
		return this.peticaoId;
	}
	
	public String getClasseId() {
		return classeId;
	}

	public Boolean isValida() {
		return valida;
	}
	
	public String getMotivo() {
		return motivo;
	}
	
	public List<String> getPartesPoloAtivo() {
		return partesPoloAtivo;
	}
	
	public List<String> getPartesPoloPassivo() {
		return partesPoloPassivo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
