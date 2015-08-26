package br.jus.stf.autuacao.interfaces.dto;

import java.util.List;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Representa a petição enviada pelo peticionador")
public class PeticaoDto {

	@ApiModelProperty(value = "Classe processual sugerida pelo peticionador")
	private String classe;
	
	@ApiModelProperty(value = "A lista de partes do polo ativo e a lista de partes do polo passivo")
	private Map<String, List<String>> partes;
	
	@ApiModelProperty(value = "A lista de documentos anexados pelo peticionador")
	private List<String> documentos;
	
	public PeticaoDto(String classe, Map<String, List<String>> partes) {
		this.classe = classe;
		this.partes = partes;
	}

	public String getClasse() {
		return classe;
	}
	
	public Map<String, List<String>> getPartes() {
		return partes;
	}
	
	public List<String> getDocumentos() {
		return documentos;
	}
	
}
