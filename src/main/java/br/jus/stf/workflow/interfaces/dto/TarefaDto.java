package br.jus.stf.workflow.interfaces.dto;

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
	private Long processo;

	public TarefaDto(Long id, String nome, String descricao, Long processo) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.processo = processo;
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
	
	public Long getProcesso(){
		return this.processo;
	}

}
