package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Classe responsável por transportar os dados do indicador Quantidade de Petições Autuadas.
 * 
 * @author Anderson Araújo
 * @since 09.11.2015
 *
 */
@ApiModel(value = "Classe responsável por transportar os dados do indicador Quantidade de Petições Autuadas.")
public class QuantidadePeticoesAutuadasDto {
	
	@ApiModelProperty("Classe processual da petição autuada.")
	private String classeProcessual;
	
	@ApiModelProperty("Data de cadastro da petição autuada.")
	private Integer quantidadePeticoesAutuadas;
	
	public QuantidadePeticoesAutuadasDto(String classeProcessual, Integer quantidadePeticoesAutuadas){
		this.classeProcessual = classeProcessual;
		this.quantidadePeticoesAutuadas = quantidadePeticoesAutuadas;
	}
	
	public String getClasseProcessual() {
		return this.classeProcessual;
	}
	
	public void setClasseProcessual(String classeProcessual) {
		this.classeProcessual = classeProcessual;
	}
	
	public Integer getQuantidadePeticoesAutuadas() {
		return this.quantidadePeticoesAutuadas;
	}
	
	public void setQuantidadePeticoesAutuadas(Integer quantidadePeticoesAutuadas) {
		this.quantidadePeticoesAutuadas = quantidadePeticoesAutuadas;
	}
}
