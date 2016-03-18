package br.jus.stf.plataforma.workflow.interfaces.dto;


/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
public class TarefaDto {

	private Long id;
	private String nome;
	private String descricao;
	private Long processoWorkflowId;
	private String responsavel;
	private boolean dono;
	private MetadadoDto metadado;

	public TarefaDto(Long id, String nome, String descricao, String responsavel, boolean dono, Long processoWorkflow, MetadadoDto metadado) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.responsavel = responsavel;
		this.dono = dono;
		this.processoWorkflowId = processoWorkflow;
		this.metadado = metadado;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public boolean isDono() {
		return dono;
	}
	
	public String getResponsavel() {
		return responsavel;
	}
	
	public Long getProcessoWorkflowId() {
		return processoWorkflowId;
	}
	
	public MetadadoDto getMetadado() {
		return metadado;
	}
	
}
