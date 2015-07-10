package br.jus.stf.plataforma.workflow.interfaces.dto;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
public class TarefaDto {

	private String id;
	private String nome;

	public TarefaDto() {
	}
	
	public TarefaDto(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
