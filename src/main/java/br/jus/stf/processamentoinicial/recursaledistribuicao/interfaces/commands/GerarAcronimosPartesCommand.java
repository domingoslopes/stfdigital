package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Comando usado para gerar os acrônimos das partes de um processo.
 * 
 * @author anderson.araujo
 * @since 07.04.2016
 *
 */
@ApiModel(value="Comando usado para enviar partes de um processo para gerar os seus respectivos acrônimos")
public class GerarAcronimosPartesCommand {
	@ApiModelProperty(value="Partes do polo ativo do processo.")
	private List<String> partesPoloAtivo;
	
	@ApiModelProperty(value="Partes do polo passivo do processo.")
	private List<String> partesPoloPassivo;
	
	@ApiModelProperty(value="Partes do polo interessados do processo.")
	private List<String> partesPoloInteressados;
	
	public List<String> getPartesPoloAtivo(){
		return partesPoloAtivo;
	}
	
	public List<String> getPartesPoloPassivo(){
		return partesPoloPassivo;
	}
	
	public List<String> getPartesPoloInteressados(){
		return partesPoloInteressados;
	}
}
