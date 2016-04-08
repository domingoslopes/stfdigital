package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Objeto usado para transportar os dados de acrônimo de partes de um processo.
 * 
 * @author anderson.araujo
 * @since 07.04.2016
 *
 */
@ApiModel(value = "Representa um acrônimo de um parte processual")
public class AcronimoParteDto {
	@ApiModelProperty(value = "Nome da parte.")
	private String parte;
	
	@ApiModelProperty(value = "Acrônimo do nome da parte.")
	private String acronimo;
	
	public AcronimoParteDto(String parte, String acronimo){
		this.parte = parte;
		this.acronimo = acronimo;
	}
	
	public String getParte(){
		return parte;
	}
	
	public String getAcronimo(){
		return acronimo;
	}
}
