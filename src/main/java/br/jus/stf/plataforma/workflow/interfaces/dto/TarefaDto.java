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
	private Long processoWorkflow;
	private MetadadoDto metadado;
	private String tipoInformacao;

	public TarefaDto(Long id, String nome, String descricao, Long processoWorkflow, MetadadoDto metadado, String tipoInformacao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.processoWorkflow = processoWorkflow;
		this.metadado = metadado;
		this.tipoInformacao = tipoInformacao;
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public Long getProcessoWorkflow() {
		return this.processoWorkflow;
	}
	
	public MetadadoDto getMetadado() {
		return this.metadado;
	}

	public String getTipoInformacao() {
		return this.tipoInformacao;
	}
}
