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
	private String descricao;
	private String idProcesso;

	public TarefaDto() {
	}

	public TarefaDto(String id, String nome, String descricao, String idProcesso) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.idProcesso = idProcesso;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setIdProcesso(String idProcesso){
		this.idProcesso = idProcesso;
	}
	
	public String getIdProcesso(){
		return this.idProcesso;
	}

}
