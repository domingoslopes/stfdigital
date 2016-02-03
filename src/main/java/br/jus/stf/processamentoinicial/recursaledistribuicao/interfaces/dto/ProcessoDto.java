package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import br.jus.stf.jurisprudencia.controletese.interfaces.dto.TeseDto;
import br.jus.stf.processamentoinicial.autuacao.interfaces.dto.PecaDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.domain.model.ProcessoRecursal;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.AssuntoDto;


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
	
	@JsonView(ProcessoRecursal.class)
	@ApiModelProperty("Teses do processo")
	private List<TeseDto> teses;
	
	@JsonView(ProcessoRecursal.class)
	@ApiModelProperty("Assuntos vinculados ao processo")
	private List<AssuntoDto> assuntos;
	
	@JsonView(ProcessoRecursal.class)
	@ApiModelProperty("Observações da análise")
	private String observacaoAnalise;
	
	public ProcessoDto(Long id, String classe, Long numero, Long relator, Map<String, List<Long>> partes, List<PecaDto> pecas, String situacao, List<Long> preferencias, String identificacao) {
		this.id = id;
		this.classe = classe;
		this.numero = numero;
		this.relator = relator;
		this.partes = partes;
		this.pecas = pecas;
		this.situacao = situacao;
		this.preferencias = preferencias;
		this.identificacao = identificacao;
	}
	
	public ProcessoDto(Long id, String classe, Long numero, Long relator, Map<String, List<Long>> partes, List<PecaDto> pecas, String situacao, List<Long> preferencias, String identificacao, List<TeseDto> teses, List<AssuntoDto> assuntos, String observacaoAnalise) {
		this(id, classe, numero, relator, partes, pecas, situacao, preferencias, identificacao);
		this.teses = teses;
		this.assuntos = assuntos;
		this.observacaoAnalise = observacaoAnalise;
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
}
