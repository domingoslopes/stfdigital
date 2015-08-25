package br.jus.stf.autuacao.interfaces.commands;

import java.util.List;

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
	private String classe;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo ativo", required=true)
	private List<String> partesPoloAtivo;
	
	@NotEmpty
	@ApiModelProperty(value = "Lista com as partes do polo passivo", required=true)
	private List<String> partesPoloPassivo;
	
	@ApiModelProperty(value = "A lista de identificadores dos documentos que serão anexados pelo peticionador", required=true)
	private List<String> documentos;

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getClasse() {
		return classe;
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
	
	public void setDocumentos(List<String> documentos) {
		this.documentos = documentos;
	}
	
	public List<String> getDocumentos() {
		return documentos;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this); 
	}
	
}
