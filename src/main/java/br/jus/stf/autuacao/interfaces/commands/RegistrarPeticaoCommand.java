package br.jus.stf.autuacao.interfaces.commands;

import java.util.List;
import java.util.Map;

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
@ApiModel(value = "A Petição referente à nova ação que se deseja propor ao Supremo Tribunal Federal")
public class RegistrarPeticaoCommand {

	@NotBlank
	@ApiModelProperty(value = "Classe Processual Sugerida", required=true)
	private String classeProcessual;
	
	@NotEmpty
	@ApiModelProperty(value = "Duas listas: uma lista com partes do polo ativo e outra com partes do polo passivo", required=true)
	private Map<String, List<String>> partes;
	
	@NotEmpty
	@ApiModelProperty(value = "A lista de documentos que serão anexados pelo peticionador", required=true)
	private List<String> documentos;
	
	public void setClasseProcessual(String classeProcessual) {
		this.classeProcessual = classeProcessual;
	}

	public String getClasseProcessual() {
		return classeProcessual;
	}
	
	public void setPartes(Map<String, List<String>> partes) {
		this.partes = partes;
	}
	
	public Map<String, List<String>> getPartes() {
		return partes;
	}
	
	public void setDocumentos(List<String> documentos) {
		this.documentos = documentos;
	}
	
	public List<String> getDocumentos() {
		return documentos;
	}
	
}
