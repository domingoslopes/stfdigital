package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import java.util.List;
import java.util.Map;

import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.MotivoInaptidaoDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TeseDto;

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
	
	@ApiModelProperty(value = "A lista de peças anexadas.")
	private List<PecaDto> pecas;
	
	@ApiModelProperty(value = "Dituação do processo.")
	private String situacao;
	
	@ApiModelProperty(value = "A lista de preferências.")
	private List<Long> preferencias;
	
	@ApiModelProperty(value = "Identificação do processo.")
	private String identificacao;
	
	@ApiModelProperty(value = "Assuntos vinculados ao processo.")
	private List<AssuntoDto> assuntos;
	
	@ApiModelProperty(value = "Teses vinculadas ao processo.")
	private List<TeseDto> teses;
	
	public ProcessoDto(Long id, String classe, Long numero, Long relator, Map<String, List<Long>> partes, List<PecaDto> pecas, String situacao, 
			List<Long> preferencias, String identificacao, List<AssuntoDto> assuntos, List<TeseDto> teses) {
		this.id = id;
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
		this.partes = partes;
		this.pecas = pecas;
		this.situacao = situacao;
		this.preferencias = preferencias;
		this.identificacao = identificacao;
		this.assuntos = assuntos;
		this.teses = teses;
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
	
	public String getSituacao(){
		return situacao;
	}
	
	public List<Long> getPreferencias() {
		return preferencias;
	}
	
	public String getIdentificacao(){
		return identificacao;
	}
	
	public List<AssuntoDto> getAssuntos() {
		return assuntos;
	}
	
	public List<TeseDto> getTeses() {
		return teses;
	}
	
}
