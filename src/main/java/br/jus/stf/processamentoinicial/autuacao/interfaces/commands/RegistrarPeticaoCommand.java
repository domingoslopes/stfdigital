package br.jus.stf.processamentoinicial.autuacao.interfaces.commands;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Contém as informações para registro de uma nova petição digital")
public class RegistrarPeticaoCommand {

	@NotBlank
	@ApiModelProperty(value = "Identificador da classe processual sugerida pelo peticionador", required=true)
	private String classeId;
	
	@ApiModelProperty(value = "Identificador do Órgão para o qual o seu representante está peticionando, se for o caso", required=false)
	private Long orgaoId;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private List<String> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private List<String> partesPoloPassivo;
	
	@NotEmpty
	@ApiModelProperty(value = "A lista de identificadores das peças que serão anexados pelo peticionador", required=true)
	private List<Map<String, String>> pecas;

	public void setClasseId(String classeId) {
		this.classeId = classeId;
	}

	public String getClasseId() {
		return classeId;
	}
	
	public void setOrgaoId(Long orgaoId) {
		this.orgaoId = orgaoId;
	}
	
	public Long getOrgaoId() {
		return orgaoId;
	}
	
	public void setPartesPoloAtivo(List<String> partesPoloAtivo) {
		this.partesPoloAtivo = partesPoloAtivo;
	}
	
	public List<String> getPartesPoloAtivo() {
		return partesPoloAtivo;
	}
	
	public void setPartesPoloPassivo(List<String> partesPoloPassivo) {
		this.partesPoloPassivo = partesPoloPassivo;
	}
	
	public List<String> getPartesPoloPassivo() {
		return partesPoloPassivo;
	}
	
	public void setPecas(List<Map<String, String>> pecas) {
		this.pecas = pecas;
	}
	
	public List<Map<String, String>> getPecas() {
		return pecas;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
