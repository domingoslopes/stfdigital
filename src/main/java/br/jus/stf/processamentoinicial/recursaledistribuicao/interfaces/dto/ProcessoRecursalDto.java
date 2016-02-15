package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import java.util.List;
import java.util.Map;

import br.jus.stf.jurisprudencia.controletese.interfaces.dto.TeseDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;

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
public class ProcessoRecursalDto extends ProcessoDto {

	@ApiModelProperty("Teses do processo")
	private List<TeseDto> teses;
	
	@ApiModelProperty("Assuntos vinculados ao processo")
	private List<AssuntoDto> assuntos;
	
	@ApiModelProperty("Observações da análise")
	private String observacaoAnalise;

	public ProcessoRecursalDto(Long id, String classe, Long numero, Long relator, Map<String, List<Long>> partes,
	        List<PecaDto> pecas, String situacao, List<Long> preferencias, String identificacao, List<TeseDto> teses,
	        List<AssuntoDto> assuntos, String observacaoAnalise) {
		super(id, classe, numero, relator, partes, pecas, situacao, preferencias, identificacao);
		
		this.teses = teses;
		this.assuntos = assuntos;
		this.observacaoAnalise = observacaoAnalise;
	}

	public List<TeseDto> getTeses() {
		return teses;
	}

	public List<AssuntoDto> getAssuntos() {
		return assuntos;
	}

	public String getObservacaoAnalise() {
		return observacaoAnalise;
	}
}
