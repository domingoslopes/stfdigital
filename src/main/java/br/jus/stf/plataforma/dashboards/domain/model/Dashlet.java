package br.jus.stf.plataforma.dashboards.domain.model;

public class Dashlet {

	private String nome;
	
	public Dashlet(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
