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
	private Long idProcesso;
	private Long idInformacao;

	public TarefaDto(Long id, String nome, String descricao, Long idProcesso, Long idInformacao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.idProcesso = idProcesso;
		this.idInformacao = idInformacao;
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
	
	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
	
	public Long getIdProcesso() {
		return this.idProcesso;
	}
	
	public Long getIdInformacao() {
		return idInformacao;
	}

}
