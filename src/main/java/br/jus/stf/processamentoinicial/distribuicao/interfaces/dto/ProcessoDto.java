package br.jus.stf.processamentoinicial.distribuicao.interfaces.dto;

import java.util.List;
import java.util.Map;

import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * @author Rodrigo Barreiros
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 * @since 19.08.2015
 */
@ApiModel(value = "Representa um processo distribuído")
public class ProcessoDto {
	
	@ApiModelProperty(value = "Id do processo")
	private Long id;
	
	@ApiModelProperty(value = "Número do processo")
	private Long numero;
	
	@ApiModelProperty(value = "Classe do processo")
	private String classe;
	
	@ApiModelProperty(value = "Relator do processo")
	private Long relator;
	
	@ApiModelProperty(value = "A lista de partes do polo ativo e a lista de partes do polo passivo")
	private Map<String, List<Long>> partes;
	
	@ApiModelProperty(value = "A lista de peças anexadas")
	private List<PecaDto> pecas;
	
	public ProcessoDto(Long id, String classe, Long numero, Long relator, Map<String, List<Long>> partes, List<PecaDto> pecas) {
		this.id = id;
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
		this.partes = partes;
		this.pecas = pecas;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public Long getRelator() {
		return relator;
	}
	
	public Map<String, List<Long>> getPartes() {
		return partes;
	}
	
	public List<PecaDto> getPecas() {
		return pecas;
	}
	
}
