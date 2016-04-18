package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import java.util.List;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.jurisprudencia.controletese.interfaces.dto.TeseDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;

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
	
	@ApiModelProperty("Motivos de inaptidão do processo")
	private List<String> motivosInaptidao;
	
	@ApiModelProperty("Observações da análise")
	private String observacaoAnalise;

	public ProcessoRecursalDto(Long id, String classe, Long numero, Long relator, Map<String, List<Long>> partes,
	        List<PecaDto> pecas, List<Long> preferencias, String identificacao, List<TeseDto> teses,
	        List<AssuntoDto> assuntos, List<String> motivosInaptidao, String observacaoAnalise) {
		super(id, classe, numero, relator, partes, pecas, preferencias, identificacao);
		
		this.teses = teses;
		this.assuntos = assuntos;
		this.motivosInaptidao = motivosInaptidao;
		this.observacaoAnalise = observacaoAnalise;
	}

	public List<TeseDto> getTeses() {
		return teses;
	}

	public List<AssuntoDto> getAssuntos() {
		return assuntos;
	}
	
	public List<String> getMotivosInaptidao() {
		return motivosInaptidao;
	}

	public String getObservacaoAnalise() {
		return observacaoAnalise;
	}
}
