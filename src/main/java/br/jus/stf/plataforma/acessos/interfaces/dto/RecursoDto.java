package br.jus.stf.plataforma.acessos.interfaces.dto;


public class RecursoDto {
	
	private Long id;
	private String nome;
	private String tipo;
	
	public RecursoDto(Long id, String nome, String tipo) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
}
