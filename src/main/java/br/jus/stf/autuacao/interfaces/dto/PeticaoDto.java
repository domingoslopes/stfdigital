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
	private Map<String, List<Long>> partes;
	
	@ApiModelProperty(value = "A lista de documentos anexados pelo peticionador")
	private List<Long> documentos;
	
	public PeticaoDto(String classe, Map<String, List<Long>> partes, List<Long> documentos) {
		this.classe = classe;
		this.partes = partes;
		this.documentos = documentos;
	}

	public String getClasse() {
		return classe;
	}
	
	public Map<String, List<Long>> getPartes() {
		return partes;
	}
	
	public List<Long> getDocumentos() {
		return documentos;
	}
	
}
